package uj.wmii.pwj.gvt;
import java.io.*;
import java.nio.file.Files;

public class Gvt {

    private final ExitHandler exitHandler;
    public Gvt(ExitHandler exitHandler) {
        this.exitHandler = exitHandler;
    }

    public static void main(String... args) {
        Gvt gvt = new Gvt(new ExitHandler());
        gvt.mainInternal(args);
    }

    void mainInternal(String... args) {
        if (args.length == 0)
            exitHandler.exit(1, "Please specify command.");
        else {
            switch (args[0]) {
                case "init": doInitialization(args); break;
                case "add" : doAddition(args);break;
                case "detach": doDetachment(args);break;
                case "commit": doCommit(args);break;
                case "checkout" : doCheckout(args);break;
                case "version" :
                    try {
                        doVersion(args);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "history" :
                    try {
                        doHistory(args);
                    } catch (Exception e) {
                        e.printStackTrace();
                        exitHandler.exit(-3, "Underlying system problem. See ERR for details.");
                    }
                    break;
                default: unknownCommand(args); break;
            }
        }
    }
    void unknownCommand(String... args) {

        StringBuilder printCommand = new StringBuilder();
        for (String arg : args) {
            printCommand.append(arg).append(" ");
        }
        printCommand = new StringBuilder(printCommand.substring(0, printCommand.length() - 1));
        exitHandler.exit(1, "Unknown command " + printCommand);

    }
    void writeVersion(File path, String version) {
        try {
            BufferedWriter writeVersion = new BufferedWriter(new FileWriter(path.getAbsolutePath()));
            writeVersion.write(version);
            writeVersion.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void addToHistory(File path, String version, String message) {

        try {
            BufferedWriter writeHistory = new BufferedWriter(new FileWriter(path.getAbsolutePath(), true));
            writeHistory.write(version);
            writeHistory.write(message);
            writeHistory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void doInitialization(String ...args) {
        if (args.length != 1)
            unknownCommand(args);
        else {
            try {
                initializeRepo();
            } catch (Exception e) {
                e.printStackTrace();
                exitHandler.exit(-3, "Underlying system problem. See ERR for details.");
            }
        }
    }
    void initFiles(String path) {


        File version = new File(path + File.separator + "current.txt");
        File history = new File(path + File.separator + "history.txt");

        try {
            if (version.createNewFile() && history.createNewFile()) {
                writeVersion(version, "0");
                addToHistory(history, "Version: 0\n", "GVT initialized.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitHandler.exit(-3, "Underlying system problem. See ERR for details.");
        }

    }
    void initializeRepo() {
        try {
            File initFolder = new File(".gvt");

            String path = initFolder.getAbsolutePath();

            if (!initFolder.mkdir()) {
                exitHandler.exit(10, "Current directory is already initialized.");
            } else {

                initFiles(path);

                exitHandler.exit(0, "Current directory initialized successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitHandler.exit(-3, "Underlying system problem. See ERR for details.");
        }
    }
    void doAddition(String ...args) {
        if (args.length == 1)
            exitHandler.exit(20, "Please specify file to add.");
        if (args.length >= 4)
            unknownCommand();
        else {
            if (args.length == 3 && !(args[2].startsWith("-m \"") || args[2].charAt(args[2].length() - 1) != '\"'))
                unknownCommand();
            else {
                try {
                    addFileToRepo(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    exitHandler.exit(22, "File cannot be added. See ERR for details. File: " + args[1]);
                }
            }
        }
    }
    void doDetachment(String ...args) {
        if (args.length >= 4)
            unknownCommand();
        if (args.length == 1)
            exitHandler.exit(30, "Please specify file to detach.");
        else {
            if (args.length == 3 && !(args[2].startsWith("-m \"") || args[2].charAt(args[2].length() - 1) != '\"'))
                unknownCommand();
            else {
                try {
                    detachFileFromRepo(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    exitHandler.exit(31, "File cannot be detached, see ERR for details. File: " + args[1]);
                }
            }
        }
    }
    void doCommit(String ...args) {
        if (args.length >=4)
            unknownCommand();
        if (args.length == 1)
            exitHandler.exit(50, "Please specify file to commit.");
        else {
            if (args.length == 3 && !(args[2].startsWith("-m \"") || args[2].charAt(args[2].length() - 1) != '\"'))
                unknownCommand();
            else {
                try {
                    commitFileToRepo(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    exitHandler.exit(52, "File cannot be committed, see ERR for details. File: " + args[1]);
                }
            }
        }
    }

    void doCheckout(String ...args) {
        if (args.length >= 3)
            unknownCommand();
        if (args.length == 1) {
            exitHandler.exit(40, "Invalid version number: ");
        }
        if (args.length == 2) {
            try {
                long ver = Long.parseLong(args[1]);
                checkoutFiles(ver);
            } catch (Exception e){
                exitHandler.exit(40, "Invalid version number: " + args[1]);
            }
        }
    }
    void doVersion(String ...args) throws IOException {
        if (args.length == 1) {
            File cur = new File("");
            File findVersion = new File(cur.getAbsolutePath() + File.separator + ".gvt" + File.separator + "current.txt");
            long version = getVersion(findVersion);
            printVersion(version);
        } else {
            try {
                long val = Long.parseLong(args[1]);
                printVersion(val);
            } catch (Exception e) {
                exitHandler.exit(60, "Invalid version number: " + args[1]);
            }
        }
    }
    void printVersion(long version) throws IOException {
        File cur = new File("");
        File history = new File(cur.getAbsolutePath() + File.separator + ".gvt" + File.separator + "history.txt");
        BufferedReader readHistory = new BufferedReader(new FileReader(history));
        String read;
        boolean x = false;
        while((read = readHistory.readLine()) != null) {
            if (x && read.equals("Version: " + (version + 1)))
                break;
            if (x) {
                System.out.println(read);
            }
            if (read.equals("Version: " + version)) {
                System.out.println(read);
                x = true;
            }
        }
        if (!x) {
            exitHandler.exit(60, "Invalid version number: " + version);
        }
    }
    void printHistory(long versionStart) throws IOException {
        File cur = new File("");
        File history = new File(cur.getAbsolutePath() + File.separator + ".gvt" + File.separator + "history.txt");
        BufferedReader readHistory = new BufferedReader(new FileReader(history));
        String read;
        while ((read = readHistory.readLine()) != null) {
            if (read.equals("Version: " + versionStart)) {
                System.out.print(versionStart + ": ");
                read = readHistory.readLine();
                System.out.println(read);
                versionStart ++;
            }
        }
    }
    void doHistory(String ...args) throws IOException {
        long versionStart = 0;
        File cur = new File("");
        File findVersion = new File(cur.getAbsolutePath() + File.separator + ".gvt" + File.separator + "current.txt");
        long version = getVersion(findVersion);
        for (String arg: args) {
            if (arg.startsWith("-last ")) {
                try {
                    long val = Long.parseLong(arg.substring(6));
                    versionStart = version - val + 1;
                    if (versionStart < 0)
                        versionStart = 0;
                } catch (Exception e) {
                    versionStart = 0;
                }
            }
        }
        printHistory(versionStart);
    }
    void deleteCurrentDirectoryFiles(File current) throws IOException {
        File[] listCurrent = current.getAbsoluteFile().listFiles();
        File fatherFile = new File("");
        if (listCurrent != null) {


            for (File child : listCurrent) {
                if (!child.getAbsolutePath().equals(fatherFile.getAbsolutePath() + File.separator + ".gvt") &&
                        !child.getAbsolutePath().equals(fatherFile.getAbsolutePath() + File.separator + ".idea") &&
                        !child.getAbsolutePath().equals(fatherFile.getAbsolutePath() + File.separator + "JavaGVt.iml") &&
                        !child.getAbsolutePath().equals(fatherFile.getAbsolutePath() + File.separator + "out") &&
                        !child.getAbsolutePath().equals(fatherFile.getAbsolutePath() + File.separator + "src")) {
                    File toDelete = new File(child.getAbsolutePath());
                    Files.delete(toDelete.toPath());
                }
            }
        }
    }
    void checkoutFiles(long versionToCheck) throws IOException {
        File current = new File("");
        File prev = new File(current.getAbsolutePath() + File.separator + ".gvt" + File.separator + "Version " + versionToCheck);
        if (!prev.exists() && versionToCheck != 0) {
            exitHandler.exit(40, "Invalid version number: " + versionToCheck);
        }
        deleteCurrentDirectoryFiles(current);
        if (versionToCheck == 0) {
            exitHandler.exit(0, "Version 0 checked out successfully.");
        }
        if (versionToCheck != 0) {
            copyFiles(prev, current);
            exitHandler.exit(0, "Version " + versionToCheck + " checked out successfully.");
        }
    }
    void copyFiles(File prev, File ver, String ...args) {

        try {

            File[] directoryListing = prev.getAbsoluteFile().listFiles();

            if (directoryListing != null) {

                for (File child : directoryListing) {
                    try {
                        Files.copy(child.getAbsoluteFile().toPath(), new File(ver.getAbsoluteFile() + File.separator + child.getName()).toPath());
                    } catch (Exception e) {
                        catchingExceptionsManager(args[0], args[1], e);
                    }
                }
            }
        } catch (Exception e) {
            catchingExceptionsManager(args[0], args[1], e);
        }

    }
    void copyBegin(File prev, File ver, File addFile, String ...args) {
        try {
            if (args[0].equals("add") && ver.mkdir()) {

                Files.copy(new File(addFile.getAbsoluteFile().getParentFile() + File.separator + args[1]).toPath(),
                        new File(ver.getAbsoluteFile() + File.separator + args[1]).toPath());
                copyFiles(prev, ver, args);

            }

            if ((args[0].equals("detach") || args[0].equals("commit")) && ver.mkdir()) {

                copyFiles(prev, ver, args);

            }
        } catch (Exception e) {
            catchingExceptionsManager(args[0], args[1], e);
        }
    }
    void deleteManager(File ver, String ...args) {
        File check = new File(ver.getAbsolutePath() + File.separator + args[1]);
        try {
            Files.delete(check.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            exitHandler.exit(31, "File cannot be detached, see ERR for details. File: " + args[1]);
        }
    }
    void copyFromMainCatalogue(File copyTo, File copyFrom) throws IOException {
        Files.copy(copyFrom.getAbsoluteFile().toPath(), copyTo.getAbsoluteFile().toPath());
    }
    void successfulMessage(String command, String fileName) {
        if (command.equals("add"))
            exitHandler.exit(0,"File added successfully. File: " + fileName);
        if (command.equals("detach"))
            exitHandler.exit(0,"File detached successfully. File: " + fileName);
        if (command.equals("commit"))
            exitHandler.exit(0,"File committed successfully. File: " + fileName);
    }
    void versionManager(File version, File history, File prev, File ver, File file, String ...args) {

        try {
            copyBegin(prev, ver, file, args);
            if (args[0].equals("detach")) {
                deleteManager(ver, args);
            }
            if (args[0].equals("commit")) {
                deleteManager(ver, args);
                copyFromMainCatalogue(new File(ver.getAbsoluteFile() + File.separator + args[1]), file);
            }
            writeVersion(version, String.valueOf(getVersion(version) + 1));
            String[] message = null;
            if (args.length == 3) {
                String withoutQuotes = args[2].substring(4, args[2].length() - 1);
                message = withoutQuotes.split("\\R");
            }
            String finalMessage = messageManager(args[0], file.getName());
            if (message != null) {
                addToHistory(history, "\nVersion: " + (getVersion(version)) + "\n", finalMessage + " " + args[2].substring(4, args[2].length() - 1));
            } else {
                addToHistory(history, "\nVersion: " + (getVersion(version)) + "\n", finalMessage);
            }
            successfulMessage(args[0], file.getName());
        } catch (Exception e) {
            catchingExceptionsManager(args[0], args[1], e);
        }

    }

    String messageManager(String command, String file) {
        if (command.equals("add"))
            return "Added file: " + file;
        if (command.equals("detach"))
            return "Detached file: " + file;
        if (command.equals("commit"))
            return "Committed file: " + file;
        return "";
    }
    void catchingExceptionsManager(String command, String fileName, Exception e) {
        if (command.equals("add")) {
            e.printStackTrace();
            exitHandler.exit(22, "File cannot be added. See ERR for details. File: " + fileName);
        }
        if (command.equals("detach")) {
            e.printStackTrace();
            exitHandler.exit(31, "File cannot be detached. See ERR for details. File: " + fileName);
        }
        if (command.equals("commit")) {
            e.printStackTrace();
            exitHandler.exit(52, "File cannot be committed. See ERR for details. File: " + fileName);
        }
    }
    void changingCommandsManager(String path, File addFile, String ...args) {
        try {
            File version = new File(path + File.separator + "current.txt");
            File prev = new File(path + File.separator + "Version " + getVersion(version));
            File ver = new File(path + File.separator + "Version " + (getVersion(version) + 1));
            File check = new File(prev + File.separator + args[1]);
            File history = new File(path + File.separator + "history.txt");

            if (args[0].equals("add")) {
                if (!prev.exists() || !check.exists()) {
                    versionManager(version, history, prev, ver, addFile, args);
                } else {
                    exitHandler.exit(0, "File already added. File: " + args[1]);
                }
            } else {

                versionManager(version, history, prev, ver, addFile, args);

            }
        } catch (Exception e) {
            catchingExceptionsManager(args[0], args[1], e);
        }
    }
    void addFileToRepo(String ...args) {
        File addFile = new File(args[1]);

        if (checkInitialization(addFile))
            exitHandler.exit(-2, "Current directory is not initialized. Please use init command to initialize.");

        if (!addFile.exists())
            exitHandler.exit(21, "File not found. File: " + args[1]);

        String path = addFile.getAbsoluteFile().getParentFile() + File.separator + ".gvt";
        try {
            changingCommandsManager(path, addFile, args);
        } catch (Exception e) {
            e.printStackTrace();
            exitHandler.exit(22, "File cannot be added. See ERR for details. File: " + args[1]);
        }
    }
    void changeBeginManager(String ...args) throws IOException {
        File file = new File(args[1]);

        if (checkInitialization(file))
            exitHandler.exit(-2, "Current directory is not initialized. Please use \"init\" command to initialize.");

        String path = file.getAbsoluteFile().getParentFile() + File.separator + ".gvt";

        File version = new File(path + File.separator + "current.txt");
        File prev = new File(path + File.separator + "Version " + getVersion(version) + File.separator + args[1]);

        if (!prev.exists()) {
            exitHandler.exit(0, "File is not added to gvt. File: " + args[1]);
        }

        changingCommandsManager(path, file, args);
    }

    void detachFileFromRepo(String ...args) throws IOException {
        changeBeginManager(args);
    }
    void commitFileToRepo(String ...args) throws IOException {
        changeBeginManager(args);
    }
    long getVersion(File file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile().getParentFile() + File.separator + "current.txt"));
        String read = br.readLine();
        return Long.parseLong(read);
    }
    boolean checkInitialization(File check) {

        String path = check.getAbsoluteFile().getParentFile() + File.separator + ".gvt";
        File checkFile = new File(path);
        return !checkFile.exists();

    }

}

