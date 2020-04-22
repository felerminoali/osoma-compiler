package mz.edu.osoma.javacompiler.controller;

import java.util.HashMap;
import java.util.Map;

public class Output {
    private Map<String, Object> resultExec = new HashMap<String, Object>();
    private boolean flag = true;


    public Map<String, Object> getResultExec() {
        return this.resultExec;
    }

    public void setResultExec(String key, Object value) {
        if(key.equals("stdout") || key.equals("stderror")){
            String current =  resultExec.get(key) == null ? "" :  (String) resultExec.get(key);
            value = current+value;
        }
        resultExec.put(key,value);
    }

//    public synchronized Map<String, Object> getResultExec() {
//
//
//        while (flag) { // not the producer's turn
//            // thread that called this method must wait
//            try {
//                wait();
//            }
//            // process Interrupted exception while thread waiting
//            catch (InterruptedException exception) {
//                exception.printStackTrace();
//            }
//        }
//
//        flag = true;
//        notify();
//        return this.resultExec;
//    }
//
//    public synchronized void setResultExec(String key, Object value) {
//
//        System.out.println("entrei"+key);
//
//        while (!flag) { // not the producer's turn
//            // thread that called this method must wait
//            try {
//                wait();
//            }
//            // process Interrupted exception while thread waiting
//            catch (InterruptedException exception) {
//                exception.printStackTrace();
//            }
//        }
//
//        flag = false;
//
//        if(key.equals("stdout") || key.equals("stderror")){
//            String current =  resultExec.get(key) == null ? "" :  (String) resultExec.get(key);
//            value = current+value;
//        }
//
//
//        resultExec.put(key,value);
//        notify();
//    }

}
