package info.galudisu

class Individual private constructor(val nickname: String) {
  companion object {
    fun newSubscribingUser(email: String) = Individual(email.substringBefore('@'))

    fun newFacebookUser(accountId: Int) = Individual(getFacebookName(accountId))

    private fun getFacebookName(accountId: Int): String = accountId.toString()
  }
}
