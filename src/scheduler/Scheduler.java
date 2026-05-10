/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication1;

/**
 *
 * @author LENOVO
 */
import java.util.*;

public class Scheduler {

    // 1. Round Robin - إصلاح منطق الطابور
    public static List<MyProcess> simulateRR(List<MyProcess> processes, int quantum) {
        List<MyProcess> gantt = new ArrayList<>();
        Queue<MyProcess> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int n = processes.size();
        
        processes.sort(Comparator.comparingInt(MyProcess::getArrivalTime));
        
        boolean[] inQueue = new boolean[n];
        
        // أضف أول عملية وصلت
        for(int i=0; i<n; i++) {
            if(processes.get(i).getArrivalTime() <= currentTime) {
                queue.add(processes.get(i));
                inQueue[i] = true;
            }
        }

        while (completed < n) {
            MyProcess current = queue.poll();

            if (current == null) {
                currentTime++;
                for(int i=0; i<n; i++) {
                    if(!inQueue[i] && processes.get(i).getArrivalTime() <= currentTime) {
                        queue.add(processes.get(i));
                        inQueue[i] = true;
                    }
                }
                continue;
            }

            if (current.getResponseTime() == -1) 
                current.setResponseTime(currentTime - current.getArrivalTime());

            int runTime = Math.min(current.getRemainingTime(), quantum);
            gantt.add(new MyProcess(current.getName(), 0, runTime)); // سجل في Gantt
            
            for(int t=0; t<runTime; t++) {
                currentTime++;
                // أضف أي عملية وصلت أثناء التنفيذ قبل إعادة العملية الحالية
                for(int i=0; i<n; i++) {
                    if(!inQueue[i] && processes.get(i).getArrivalTime() <= currentTime) {
                        queue.add(processes.get(i));
                        inQueue[i] = true;
                    }
                }
            }
            
            current.setRemainingTime(current.getRemainingTime() - runTime);

            if (current.getRemainingTime() > 0) {
                queue.add(current); // تعود لنهاية الطابور
            } else {
                completed++;
                current.setCompletionTime(currentTime);
                current.setTurnAroundTime(current.getCompletionTime() - current.getArrivalTime());
                current.setWaitingTime(current.getTurnAroundTime() - current.getBurstTime());
            }
        }
        return gantt;
    }

    // 2. SJF Preemptive (SRTF) - إصلاح المقاطعة
    public static List<MyProcess> simulateSJF(List<MyProcess> processes) {
        List<MyProcess> gantt = new ArrayList<>();
        int currentTime = 0, completed = 0, n = processes.size();
        MyProcess prevProcess = null;
        int startTime = 0;

        while (completed < n) {
            MyProcess shortest = null;
            int minRemaining = Integer.MAX_VALUE;

            for (MyProcess p : processes) {
                if (p.getArrivalTime() <= currentTime && p.getRemainingTime() > 0) {
                    if (p.getRemainingTime() < minRemaining) {
                        minRemaining = p.getRemainingTime();
                        shortest = p;
                    }
                }
            }

            if (shortest == null) {
                currentTime++;
                continue;
            }

            if (shortest.getResponseTime() == -1)
                shortest.setResponseTime(currentTime - shortest.getArrivalTime());

            // لتسجيل الـ Gantt بشكل كتل
            if (prevProcess != null && !prevProcess.getName().equals(shortest.getName())) {
                gantt.add(new MyProcess(prevProcess.getName(), 0, currentTime - startTime));
                startTime = currentTime;
            }
            if (prevProcess == null) startTime = currentTime;
            
            prevProcess = shortest;
            shortest.setRemainingTime(shortest.getRemainingTime() - 1);
            currentTime++;

            if (shortest.getRemainingTime() == 0) {
                completed++;
                gantt.add(new MyProcess(shortest.getName(), 0, currentTime - startTime));
                shortest.setCompletionTime(currentTime);
                shortest.setTurnAroundTime(shortest.getCompletionTime() - shortest.getArrivalTime());
                shortest.setWaitingTime(shortest.getTurnAroundTime() - shortest.getBurstTime());
                prevProcess = null;
            }
        }
        return gantt;
    }

    // 3. SJF Non-Preemptive
    public static List<MyProcess> simulateSJF_NonPreemptive(List<MyProcess> processes) {
        List<MyProcess> gantt = new ArrayList<>();
        int currentTime = 0, completed = 0, n = processes.size();
        boolean[] isDone = new boolean[n];

        while (completed < n) {
            MyProcess best = null;
            int idx = -1;

            for (int i = 0; i < n; i++) {
                MyProcess p = processes.get(i);
                if (p.getArrivalTime() <= currentTime && !isDone[i]) {
                    if (best == null || p.getBurstTime() < best.getBurstTime()) {
                        best = p;
                        idx = i;
                    }
                }
            }

            if (best == null) {
                currentTime++;
                continue;
            }

            best.setResponseTime(currentTime - best.getArrivalTime());
            gantt.add(new MyProcess(best.getName(), 0, best.getBurstTime()));
            currentTime += best.getBurstTime();
            best.setCompletionTime(currentTime);
            best.setTurnAroundTime(best.getCompletionTime() - best.getArrivalTime());
            best.setWaitingTime(best.getTurnAroundTime() - best.getBurstTime());
            isDone[idx] = true;
            completed++;
        }
        return gantt;
    }
}