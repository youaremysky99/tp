package seedu.fma.logic.parser;

import static seedu.fma.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_EXERCISE;
import static seedu.fma.logic.parser.CliSyntax.PREFIX_REPS;

import java.util.stream.Stream;

import seedu.fma.logic.commands.AddCommand;
import seedu.fma.logic.parser.exceptions.ParseException;
import seedu.fma.model.exercise.Exercise;
import seedu.fma.model.log.Comment;
import seedu.fma.model.log.Log;
import seedu.fma.model.log.Rep;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE, PREFIX_REPS, PREFIX_COMMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_EXERCISE, PREFIX_REPS, PREFIX_COMMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Exercise exercise = ParserUtil.parseExercise(argMultimap.getValue(PREFIX_EXERCISE).get());
        Rep rep = ParserUtil.parseRep(argMultimap.getValue(PREFIX_REPS).get());
        Comment comment = ParserUtil.parseComment(argMultimap.getValue(PREFIX_COMMENT).get());

        Log log = new Log(exercise, rep, comment);

        return new AddCommand(log);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
