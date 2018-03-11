package models


import db._


class Story(val number: String, val title: String, val phase: String) {

  private def phaseLimits = Map("ready" -> Some(3),
    "dev" -> Some(2), "test" -> Some(2), "deploy" -> None)

  class ValidationException(message: String) extends RuntimeException(message)


  def moveTo(phase: String): Either[Throwable, String] = {
      try {
        validateLimit(phase)
        StoryDAO.updatePhaseByNumber(number, phase)
        Right("Card " + this.number + " is moved to " + phase + " successfully.")
      } catch {
        case exception: Throwable => Left(exception)
      }
  }


  private[this] def validateLimit(phase: String) = {
    val currentSize:Long = StoryDAO.findAllByPhase(phase).size
    if(currentSize == phaseLimits(phase).getOrElse(-1)) {
      throw new ValidationException("You cannot exceed the limit set for the phase.")
    }}


  private[this] def validate = {
    if (number.isEmpty || title.isEmpty) {
      throw new ValidationException("Both number and title are required")
    }

    if(StoryDAO.findByNumber(number) != null)
      throw new ValidationException("Non unique number!")
  }

  def save(): Either[Throwable, String] = {
    try {
      validate
      StoryDAO.save(this)
      Right("Story is created successfully")
    } catch {
      case exception: Throwable => Left(exception)
    }
  }


}

object Story {
  def findAllByPhase(phase: String):List[Story] = StoryDAO.findAllByPhase(phase)

//  def findAllByPhase(phase: String) = tx {
//    from(stories)(s => where(s.phase === phase) select (s)) map (s => s)
//  }
}