package no.dossier.app.kotlindemo.backend.docker


import com.jcabi.ssh.Shell
import com.jcabi.ssh.Ssh
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse
import no.dossier.app.kotlindemo.domain.docker.DockerContainer
import no.dossier.app.kotlindemo.domain.docker.StatusType
import java.util.*

object DockerSshUtil {
    private val SSH_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "Proc-Type: 4,ENCRYPTED\n" +
            "DEK-Info: AES-128-CBC,5AF791A44EA5DA14B89C1DDD44417EC3\n" +
            "\n" +
            "BA2hpqkjfvtucoxmHCm0n9hP53UiblExl1qDItFl4SlefpqN4Md+ofQKzyuJRF8W\n" +
            "rI/eP4iZlz2UMb/4IIx98tY+UhnMV7s98EbnI1dzT5SJYUmPzu25Dqsw9WdeeswJ\n" +
            "0Qi1rqtQXxQPjoQXrzQG95gqo4+GEMoPlE8iqjQ/FNQ/LIiR59PRmoqnpQMzHaJ4\n" +
            "u0ODw+DqsojL/GsosEKCxAlLmHAse02k5peDGKiYfR+HtE8+XpoE15iLXAyDIjne\n" +
            "L4/wXf4TBCeV0vOOhc27zUv8lsXLGfCASuBFFXcyOBVM9faExgPOP+2VfKggAjfJ\n" +
            "1ymorOD6rxK5EiH2NRcixgzYupd5mjLzhnDbt165sBMP4+VLlb6mNILhl1eZJoeR\n" +
            "/iYt5GO2+Nrya5cIp/fFRB4zWGYDf7XhmCQRvBpmu0ZRGHX7QQcvGG+UFDy+CVJv\n" +
            "EdUEHyprQX1c585oVP9cMv9EksGfThmUJ5k98qbPYt+S2mu+XMHhdsM1BIAPv8yL\n" +
            "VAAZ18GyEa5fIWKTTRftrZpjTUaeut18lOzkIG+vztoPzP3baW0YpqFWeI5dzpg2\n" +
            "xO0vtbJ8mabVFLGaRyx8UJ3Fpj2+G09RbZsyU1lXZqn62KnTFFG4sqowstr7pa1c\n" +
            "W/rRsGgGJkTgM+p1yJQaSNeJXpcdmfXp2CLf3yIEh+9Lz9XQCyBufUYZNAJkp39y\n" +
            "Pj0drt/OwgOzSXKwTigy3SUWxqgg/rfirvbA3OjscbALEatVSZ3G3Y/Lyrb1Nt9B\n" +
            "40BbPSVmMDU9MBnhxIpuAqjAvua9e45pRQyg40WfJ2SqyzBW5UFNKkc20ueYxWy2\n" +
            "iXJ7giDf7RdrM89bql8n7j8hKO63Ray9bkM4414vbdqY7OhH4FoBbeM39fbB82B8\n" +
            "LKb8tpdRt9uV1is1Xd16pCiLc03XelvHUu3eHcvvHOUEi+KqnKbF53R24UM57m+5\n" +
            "62crF1EJBZF+fqghFO285xZRYgyZeZxFRWrFSPFX6R+zXmG1h5Gx1loIW0JnJjRR\n" +
            "x+CMRsI0P5QyObf8gzt8kYj2GOfSlUdnATiZallH+n+CSYQ6SBN0WzZOagwAZGoG\n" +
            "sQoJBazkMfzGIK3NgHsCFAhN1rVniSVW7epIfyiE9Cf5N2w928L1JpNouYCnKBxx\n" +
            "d232NtlpSdcRteO3NkbF/f8C6P6mQC7feuTSQEwpMdSAiPExGJu9kONIAPR6HdbY\n" +
            "bXVZwQ4GwrJ5CbIeZNDgGNb843rpLf/0JtgNEsYDYLHsZDidFlInkpShlT9OzXZr\n" +
            "Of9u0kGs+RYPY2QF9Kz/ISgCQy3mLbWR5fS7m1YeRCcJlkdLM/W3jxz+m5RkNwf4\n" +
            "AdZImSR5axyM2XP825TIdI/JmW+FeqpSOrg+S/qu1N8WwkpabJSaf4eUsrtj3x36\n" +
            "tkCRHAjv/f+IXT4g4g+TEJzTuyFYkO9jqR85hPzAJf0VUME17DBXGyAh09W8iuyf\n" +
            "LsAWNqID1+4Qw9cJn3tBQVJFjhb+ZdqTz6mej64gyU9/PmZ8BQOAlhHseH+Acpua\n" +
            "nPVOJoFcyDoJBeO7lpcO9/jDP4c4a7wkj7XcS3XNoHvyU2TRtdlalryNGHQZzdnk\n" +
            "-----END RSA PRIVATE KEY-----\n"


    fun getDockerContainers(): List<DockerContainer> {
        val stdout = executeSshCmd("docker ps -q | xargs docker inspect --format='{{.Id}}|{{.Name}}|{{.State.Status}}|{{.Created}}|{{.State.StartedAt}}'")

        val containers = ArrayList<DockerContainer>()
        val lines = stdout.split("\n")
        for (line in lines) {
            val lineParts = line.split("|")
            if (lineParts.size == 5) {
                val dockerContainer = DockerContainer(
                        lineParts[0],
                        lineParts[1].removePrefix("/"),
                        parseStatusType(lineParts[2]),
                        parseDateTime(lineParts[3]),
                        parseDateTime(lineParts[4]));

                containers.add(dockerContainer)
            }
        }
        return containers
    }

    fun stopContainer(containerId: String): String {
        return executeSshCmd("docker stop $containerId");
    }

    private fun parseStatusType(statusStr: String): StatusType {
        return StatusType.valueOf(statusStr)
    }

//    @JvmStatic
//    fun main(args: Array<String>) {
//        println(parseDateTime("2019-03-19T12:22:06.159971014Z"))
//    }

    private fun parseDateTime(dateStr: String): DateTime {
        val dateStrNoNano = dateStr.subSequence(0, dateStr.indexOf(".")).toString()
        val dateFormat: DateFormat = DateFormat("yyyy-MM-dd'T'HH:mm:ss")
        return dateFormat.parse(dateStrNoNano).local;
    }

    private fun executeSshCmd(cmd: String) : String {
        val shell = Ssh("internal.dossier.no", 22, "ec2-user", SSH_KEY, "ec2-user")
        return Shell.Plain(shell).exec(cmd);
    }
}
