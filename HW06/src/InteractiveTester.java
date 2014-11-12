import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tests SkipList interactively.
 *
 * @author Christopher Tam
 *
 */

public class InteractiveTester {
    private static Scanner input = new Scanner(System.in);
    private static SkipList<Integer> list = new SkipList<Integer>(
            new CoinFlipper());
    private static Random random = new Random();
    private static Pattern numbers = Pattern.compile("\\d+");

    public static void main(String[] args) {
        System.out.println("Welcome to the SkipList Interactive Tester.");
        System.out
                .println("This assumes the SkipList has a working toString() function.\r\n");

        System.out.print("Enter command (or type help for options): ");
        String command = input.nextLine().toLowerCase().trim();
        while (!command.contains("exit")) {
            if (command.startsWith("first")) {
                first();
            } else if (command.startsWith("last")) {
                last();
            } else if (command.startsWith("contains")) {
                contains(command);
            } else if (command.startsWith("put")) {
                put(command);
            } else if (command.startsWith("remove")) {
                remove(command);
            } else if (command.startsWith("get")) {
                get(command);
            } else if (command.startsWith("size")) {
                size();
            } else if (command.startsWith("clear")) {
                clear();
            } else if (command.startsWith("dataset")) {
                dataSet();
            } else if (command.startsWith("fill")) {
                fill(command);
            } else if (command.startsWith("print") || command.contains("string")) {
                print();
            } else {
                help();
            }

            System.out.print("\r\nEnter command (or type help for options): ");
            command = input.nextLine().toLowerCase().trim();
        }

        System.out.print("Goodbye!");
    }

    private static void help() {
        System.out.println("    first()");
        System.out.println("    last()");
        System.out.println("    contains(Integer data)");
        System.out.println("    put(Integer data)");
        System.out.println("    remove(Integer)");
        System.out.println("    get(Integer data)");
        System.out.println("    size()");
        System.out.println("    clear()");
        System.out.println("    dataSet()");
        System.out
                .println("    fill(Integer size, Integer min, Integer max)           fill list with 'size' amount of elements between min and max.");
        System.out
                .println("    print() or toString()                                  assumes working toString() method in SkipList.");
        System.out.println("    exit()");
    }

    private static Integer parse(String s) {
        s = s.replaceAll("\\D", "");
        try {
            return Integer.valueOf(s);
        } catch (Exception ex) {
            System.out.println("Unable to parse command.");
            return null;
        }
    }

    private static void first() {
        System.out.println(list.first());
    }

    private static void last() {
        System.out.println(list.last());
    }

    private static void contains(String s) {
        Integer i = parse(s);
        if (i != null) {
            System.out.println("Contains: " + list.contains(i));
        }
    }

    private static void put(String s) {
        Integer i = parse(s);
        list.put(i);
        System.out.println("Put: " + i + " (Size: " + list.size() + ")");
    }

    private static void remove(String s) {
        Integer i = parse(s);
        System.out.println("Remove: " + list.remove(i) + " (Size: " + list.size() + ")");
    }

    private static void get(String s) {
        Integer i = parse(s);
        System.out.println("Get: " + list.get(i));
    }

    private static void size() {
        System.out.println("Size: " + list.size());
    }

    private static void clear() {
        list.clear();
        System.out.println("Cleared (Size: " + list.size() + ")");
    }

    private static void dataSet() {
        System.out.println("DataSet: " + list.dataSet().toString());
    }

    private static void fill(String s) {
        Matcher match = numbers.matcher(s);

        int size, min, max;
        try {
            match.find();
            size = Integer.valueOf(match.group());
            match.find();
            min = Integer.valueOf(match.group());
            match.find();
            max = Integer.valueOf(match.group());
        } catch (Exception ex) {
            System.out.println("Unable to parse arguments.");
            return;
        }
        Integer[] added = new Integer[size];
        for (int i = 0; i < size; i++) {
            added[i] = random(min, max);
            list.put(added[i]);
        }
        System.out.println("New size: " + list.size());
    }

    private static Integer random(Integer min, Integer max) {
        return random.nextInt(max - min) + min;
    }

    private static void print() {
        System.out.println(list.toString());
    }
}
