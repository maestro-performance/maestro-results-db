package org.maestro.results.main;

import org.maestro.results.main.actions.record.*;

import static java.util.Arrays.copyOfRange;

public class RecordProgram implements Program {

    /**
     * Prints the help for the action and exit
     * @param code the exit code
     */
    private void help(int code) {
        System.out.println("Usage: maestro-cli <action>\n");

        System.out.println("Actions:");
        System.out.println("   test-fail-condition");
        System.out.println("   test-msg-property");
        System.out.println("   env-resource");
        System.out.println("   env-results");
        System.out.println("   sut");
        System.out.println("   test");

        System.out.println("----------");
        System.out.println("   help");

        System.exit(code);
    }

    public int run(String[] args) {
        if (args.length == 0) {
            System.err.println("The action is missing!");
            help(1);
        }

        String first = args[0];
        String[] newArgs = copyOfRange(args, 1, args.length);

        if (first.equals("help")) {
            help(1);
        }

        Action action;
        switch (first) {
            case "test-fail-condition": {
                action = new TestFailConditionAction(newArgs);
                break;
            }
            case "test-msg-property": {
                action = new TestMsgPropertyAction(newArgs);
                break;
            }
            case "env-resource": {
                action = new EnvironmentAction(newArgs);
                break;
            }
            case "env-results": {
                action = new ResultsAction(newArgs);
                break;
            }
            case "sut": {
                action = new SutAction(newArgs);
                break;
            }
            case "test": {
                action = new TestAction(newArgs);
                break;
            }
            default: {
                help(1);
                return 0;
            }
        }

        return action.run();
    }
}
