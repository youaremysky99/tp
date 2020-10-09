package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.Exercise;
import seedu.address.model.log.Comment;
import seedu.address.model.log.Log;
import seedu.address.model.log.Rep;
import seedu.address.model.util.Name;

/**
 * Jackson-friendly version of {@link Log}.
 */
class JsonAdaptedLog {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Log's %s field is missing!";

    private final String exercise;
    private final String dateTime;
    private final String rep;
    private final String comment;

    /**
     * Constructs a {@code JsonAdaptedLog} with the given log details.
     */
    @JsonCreator
    public JsonAdaptedLog(@JsonProperty("exercise") String exercise, @JsonProperty("dateTime") String dateTime,
                          @JsonProperty("rep") String rep, @JsonProperty("comment") String comment) {
        this.exercise = exercise;
        this.dateTime = dateTime;
        this.rep = rep;
        this.comment = comment;
    }

    /**
     * Converts a given {@code Log} into this class for Jackson use.
     */
    public JsonAdaptedLog(Log source) {
        exercise = source.getExercise().getName().toString();
        dateTime = source.getDateTime().toString();
        rep = source.getReps().value;
        comment = source.getComment().value;
    }

    /**
     * Converts this Jackson-friendly adapted log object into the model's {@code Log} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted log.
     */
    public Log toModelType() throws IllegalValueException {
        if (exercise == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Exercise.class.getSimpleName()));
        }
        // TODO: Exercise validation
        /*if (!Exercise.isValidExercise(exercise)) {
            throw new IllegalValueException(Exercise.MESSAGE_CONSTRAINTS);
        }*/
        // TODO: Exercise constructor: convert Name to existing Exercise
        final Exercise modelExercise = new Exercise(new Name("Sit up"), 30);

        // TODO: CREATE NEW DATETIME CLASS???
        if (dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }
        // TODO: LocalDateTime validation
        /*if (!LocalDateTime.isValidLocalDateTime(dateTime)) {
            throw new IllegalValueException(LocalDateTime.MESSAGE_CONSTRAINTS);
        }*/
        final LocalDateTime modelLocalDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);

        if (rep == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rep.class.getSimpleName()));
        }
        if (!Rep.isValidRep(rep)) {
            throw new IllegalValueException(Rep.MESSAGE_CONSTRAINTS);
        }
        final Rep modelRep = new Rep(rep);

        if (comment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Comment.class.getSimpleName()));
        }
        if (!Comment.isValidComment(comment)) {
            throw new IllegalValueException(Comment.MESSAGE_CONSTRAINTS);
        }
        final Comment modelComment = new Comment(comment);

        return new Log(modelExercise, modelRep, modelComment, modelLocalDateTime);
    }

}
