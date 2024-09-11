package info.galudisu

data class Job(val id: JobId, val company: Company, val role: Role, val salary: Salary)

@JvmInline
value class JobId(val value: Long)

@JvmInline
value class Company(val name: String)

@JvmInline
value class Role(val name: String)

@JvmInline
value class Salary(val value: Double)

val JOBS_DATABASE: Map<JobId, Job> =
  mapOf(
    JobId(1) to Job(JobId(1), Company("Apple Inc."), Role("Senior Software Engineer"), Salary(70_000.00)),
    JobId(2) to Job(JobId(2), Company("Microsoft"), Role("Software Engineer"), Salary(80_000.00)),
    JobId(3) to Job(JobId(3), Company("Google"), Role("High Software Engineer"), Salary(90_000.00)),
  )

val jobJsonScope =
  JsonScope<Job> {
    // ^^^ "receiver"
    """
    {
      "id": ${id.value},
      "company": ${company.name},
      "role": ${role.name},
      "salary": ${salary.value}
    }
    """.trimIndent()
  }

// "Scope"
fun interface JsonScope<A> { // dispatcher receiver
  fun A.toJson(): String
  // ^ "receiver"
}

context (JsonScope<A>)
fun <A> printAsJson(things: List<A>): String =
  things.joinToString(separator = ", ", prefix = "[", postfix = "]") { it.toJson() }

// coroutines - launch, async

// 1 - desired function is an extension of the scope itself
// 2 - desired function has no connection to the type itself
// 3 - one scope at one time

// context receivers.

fun main() {
  with(jobJsonScope) {
    print(printAsJson(JOBS_DATABASE.values.toList()))
  }
}
