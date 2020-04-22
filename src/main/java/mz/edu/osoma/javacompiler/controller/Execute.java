package mz.edu.osoma.javacompiler.controller;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Execute {

    @GetMapping(value = "/executethread", produces = "application/json")
    public Output executethread() throws Exception {

        Output output1 = new Output();
        Output output2 = new Output();


        try {
//            runProcess("pwd");
            String command = "javac -cp src src/main/java/mz/edu/osoma/javacompiler/controller/Program.java";
            ExecuteThread javac = new ExecuteThread(output1, command);
            javac.start();

            command = "java -cp src/main/java/ mz.edu.osoma.javacompiler.controller.Program";
            ExecuteThread java = new ExecuteThread(output2, command);
            java.start();

//            output2.setResultExec("command", command);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return output2;
    }

    @CrossOrigin
    @GetMapping(value = "/execute", produces = "application/json")
    public List<Output> execute() throws Exception {

        List<Output> outputs = new ArrayList<>();
        String fileName = "Program";
        String cmd = "";

        try {
//            runProcess("pwd");

            File file = new File("src/main/java/mz/edu/osoma/javacompiler/controller/"+fileName+".java");
            // creates the file
            if(file.createNewFile()){
                // creates a FileWriter Object
                FileWriter writer = new FileWriter(file);
                // Writes the content to the file
                writer.write("ola");
                writer.flush();
                writer.close();
            }



            cmd = "javac -cp src src/main/java/mz/edu/osoma/javacompiler/controller/"+fileName+".java";
            outputs.add(executeCommand(cmd));


            cmd = "java -cp src/main/java/ mz.edu.osoma.javacompiler.controller."+fileName;
            Output output = executeCommand(cmd);

            JUnitCore junit = new JUnitCore();
            Result runner = junit.run(JavaCompilerApplicationTests.class);


            List<Failure> failures = new ArrayList<>();

            for (Failure failure : runner.getFailures()) {

                failure.getException().getMessage();
            }

            output.setResultExec("test", runner);


            outputs.add(output);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return outputs;
    }


    private Output executeCommand(String command) throws Exception {

        Output output = new Output();
        try {
            long start = System.nanoTime();
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            output.setResultExec("command", command);

            String line = null;
            while ((line = in.readLine()) != null) {
                output.setResultExec("stdout", "$ " + line);
                System.out.println(command + "$ " + line);
            }

            long timeTakenNS = System.nanoTime() - start;
            output.setResultExec("time", timeTakenNS);

            BufferedReader in2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            String errorLine = null;
            boolean errorOccured = false;
            while ((errorLine = in2.readLine()) != null) {
                errorOccured = true;
                output.setResultExec("stderror", command + "$ " + errorLine);
            }

            if (!errorOccured) {
                double timeTakenMS = timeTakenNS * Math.pow(10, -6);
                timeTakenMS = Math.floor(timeTakenMS * 100) / 100;

            }
            output.setResultExec("exit", p.exitValue() + "");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }


    class ExecuteThread extends Thread {
        Output output;
        String command;
        Process p = null;

        public ExecuteThread(Output output, String command) {
            this.output = output;
            this.command = command;
        }

        public void run() {
            try {
                long start = System.nanoTime();
                p = Runtime.getRuntime().exec(command);
                BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

//                this.output.setResultExec("command", command );

                String line = null;
                while ((line = in.readLine()) != null) {
                    this.output.setResultExec("stdout", "$ " + line);
                    System.out.println(command + "$ " + line);
                }

                long timeTakenNS = System.nanoTime() - start;
//                this.output.setExecutionTime(timeTakenNS);

                BufferedReader in2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                String errorLine = null;
                boolean errorOccured = false;
                while ((errorLine = in2.readLine()) != null) {
                    errorOccured = true;
                    this.output.setResultExec("stderror", command + "$ " + errorLine);
                }

                if (!errorOccured) {
                    double timeTakenMS = timeTakenNS * Math.pow(10, -6);
                    timeTakenMS = Math.floor(timeTakenMS * 100) / 100;

                }
//                this.output.setExit(p.exitValue());
                this.output.setResultExec("exit", p.exitValue() + "");

                System.out.println("thread finish");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void destroyProc() {
            p.destroy();
        }
    }


}


