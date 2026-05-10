/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class MainUI extends Application {

    private ObservableList<MyProcess> processData = FXCollections.observableArrayList();
    private TableView<MyProcess> rrTable = createResultsTable();
    private TableView<MyProcess> sjfTable = createResultsTable();
    private HBox rrGantt = new HBox(2);
    private HBox sjfGantt = new HBox(2);
    private TextArea conclusionArea = new TextArea();
    private TextField quantumField = new TextField();
    private boolean isPreemptive = true;
    private Label sjfLabel = new Label("SJF Preemptive (Metrics Table):");

    @Override
    public void start(Stage primaryStage) {

        TextField nameField = new TextField();
        nameField.setPromptText("Process ID (e.g. P1)");
        TextField arrivalField = new TextField();
        arrivalField.setPromptText("Arrival Time");
        TextField burstField = new TextField();
        burstField.setPromptText("Burst Time");
        quantumField.setPromptText("Quantum");

        Button scenarioA = new Button("Scenario A");
        Button scenarioB = new Button("Scenario B");
        Button scenarioC = new Button("Scenario C");
        Button scenarioD = new Button("Scenario D");
        Button scenarioE = new Button("Scenario E (Invalid)");

        scenarioA.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        scenarioB.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        scenarioC.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        scenarioD.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white; -fx-font-weight: bold;");
        scenarioE.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold;");

        Button addButton = new Button("Add Process");
        Button calcButton = new Button("Run Comparison & Analysis");
        Button resetButton = new Button("Reset");
        Button toggleSJF = new Button("SJF: Preemptive");

        calcButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        resetButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-weight: bold;");
        toggleSJF.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");

        // ✅ Toggle بين Preemptive و Non-Preemptive
        toggleSJF.setOnAction(e -> {
            isPreemptive = !isPreemptive;
            if (isPreemptive) {
                toggleSJF.setText("SJF: Preemptive");
                toggleSJF.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
                sjfLabel.setText("SJF Preemptive (Metrics Table):");
            } else {
                toggleSJF.setText("SJF: Non-Preemptive");
                toggleSJF.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; -fx-font-weight: bold;");
                sjfLabel.setText("SJF Non-Preemptive (Metrics Table):");
            }
        });

        resetButton.setOnAction(e -> {
            processData.clear();
            rrTable.getItems().clear();
            sjfTable.getItems().clear();
            rrGantt.getChildren().clear();
            sjfGantt.getChildren().clear();
            conclusionArea.clear();
            nameField.clear();
            arrivalField.clear();
            burstField.clear();
            quantumField.clear();
        });

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                int arrival = Integer.parseInt(arrivalField.getText());
                int burst = Integer.parseInt(burstField.getText());

                if (name.isEmpty()) throw new Exception("Process ID is missing!");
                if (arrival < 0) throw new Exception("Arrival Time cannot be negative!");
                if (burst <= 0) throw new Exception("Burst Time must be greater than 0!");

                for (MyProcess p : processData) {
                    if (p.getName().equalsIgnoreCase(name)) {
                        throw new Exception("Duplicate Process ID!");
                    }
                }

                processData.add(new MyProcess(name, arrival, burst));
                nameField.clear();
                arrivalField.clear();
                burstField.clear();

            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter numeric values for Arrival and Burst time.");
            } catch (Exception ex) {
                showAlert("Validation Error", ex.getMessage());
            }
        });

        calcButton.setOnAction(e -> {
            try {
                if (processData.isEmpty()) {
                    showAlert("No Data", "Please add at least one process.");
                    return;
                }
                int q = Integer.parseInt(quantumField.getText());
                if (q <= 0) throw new Exception("Quantum must be greater than 0!");
                runComparison(q);
            } catch (Exception ex) {
                showAlert("Quantum Error", "Please enter a valid positive Time Quantum.");
            }
        });

        scenarioA.setOnAction(e -> loadScenarioA());
        scenarioB.setOnAction(e -> loadScenarioB());
        scenarioC.setOnAction(e -> loadScenarioC());
        scenarioD.setOnAction(e -> loadScenarioD());
        scenarioE.setOnAction(e -> loadScenarioE());

        VBox inputSection = new VBox(10,
                new Label("1. Input Panel (Workload):"),
                new HBox(10, nameField, arrivalField, burstField, addButton),
                new HBox(10, new Label("Time Quantum:"), quantumField, calcButton, resetButton, toggleSJF),
                new Label("Test Scenarios:"),
                new HBox(10, scenarioA, scenarioB, scenarioC, scenarioD, scenarioE));

        VBox rrSection = new VBox(5,
                new Label("Round Robin (Metrics Table):"),
                rrTable,
                new Label("RR Gantt Chart:"),
                rrGantt);

        VBox sjfSection = new VBox(5,
                sjfLabel,
                sjfTable,
                new Label("SJF Gantt Chart:"),
                sjfGantt);

        conclusionArea.setEditable(false);
        conclusionArea.setPrefHeight(200);

        VBox root = new VBox(15,
                inputSection,
                rrSection,
                sjfSection,
                new Label("Comparison Summary & Conclusion:"),
                conclusionArea);
        root.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(new ScrollPane(root), 950, 850));
        primaryStage.setTitle("OS Scheduler: Round Robin vs SJF Comparison");
        primaryStage.show();
    }

    private void runComparison(int quantum) {
        List<MyProcess> rrList = cloneList(processData);
        List<MyProcess> sjfList = cloneList(processData);

        List<MyProcess> rrGanttData = Scheduler.simulateRR(rrList, quantum);

        // ✅ بيختار Preemptive أو Non-Preemptive
        List<MyProcess> sjfGanttData = isPreemptive
                ? Scheduler.simulateSJF(sjfList)
                : Scheduler.simulateSJF_NonPreemptive(sjfList);

        rrTable.setItems(FXCollections.observableArrayList(rrList));
        sjfTable.setItems(FXCollections.observableArrayList(sjfList));

        drawGantt(rrGantt, rrGanttData);
        drawGantt(sjfGantt, sjfGanttData);

        generateAnalysis(rrList, sjfList, quantum);
    }

    private void loadScenarioA() {
        clearAll();
        processData.add(new MyProcess("P1", 0, 5));
        processData.add(new MyProcess("P2", 1, 3));
        processData.add(new MyProcess("P3", 2, 8));
        processData.add(new MyProcess("P4", 3, 2));
        quantumField.setText("2");
        runComparison(2);
    }

    private void loadScenarioB() {
        clearAll();
        processData.add(new MyProcess("P1", 0, 1));
        processData.add(new MyProcess("P2", 1, 2));
        processData.add(new MyProcess("P3", 2, 1));
        processData.add(new MyProcess("P4", 3, 3));
        processData.add(new MyProcess("P5", 4, 1));
        quantumField.setText("2");
        runComparison(2);
    }

    private void loadScenarioC() {
        clearAll();
        processData.add(new MyProcess("P1", 0, 6));
        processData.add(new MyProcess("P2", 0, 6));
        processData.add(new MyProcess("P3", 0, 6));
        processData.add(new MyProcess("P4", 0, 6));
        quantumField.setText("2");
        runComparison(2);
    }

    private void loadScenarioD() {
        clearAll();
        processData.add(new MyProcess("P1", 0, 20));
        processData.add(new MyProcess("P2", 2, 3));
        processData.add(new MyProcess("P3", 4, 1));
        processData.add(new MyProcess("P4", 6, 2));
        quantumField.setText("3");
        runComparison(3);
    }

    private void loadScenarioE() {
        showAlert("Scenario E - Validation Case",
                "Try these invalid inputs to test validation:\n\n"
                + "1. Leave Process ID empty → 'Process ID is missing'\n"
                + "2. Burst Time = 0 → 'Burst Time must be greater than 0'\n"
                + "3. Arrival Time = -1 → 'Arrival Time cannot be negative'\n"
                + "4. Add P1 twice → 'Duplicate Process ID'\n"
                + "5. Quantum = 0 → 'Quantum must be greater than 0'");
    }

    private void clearAll() {
        processData.clear();
        rrTable.getItems().clear();
        sjfTable.getItems().clear();
        rrGantt.getChildren().clear();
        sjfGantt.getChildren().clear();
        conclusionArea.clear();
    }

    private void drawGantt(HBox box, List<MyProcess> results) {
        box.getChildren().clear();
        int currentTime = 0;

        for (MyProcess p : results) {
            int width = p.getBurstTime() * 30;

            javafx.scene.shape.Rectangle rect =
                    new javafx.scene.shape.Rectangle(width, 40);
            rect.setFill(javafx.scene.paint.Color.web("#BBDEFB"));
            rect.setStroke(javafx.scene.paint.Color.web("#1976D2"));

            Label nameLabel = new Label(p.getName());
            nameLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

            StackPane rectPane = new StackPane(rect, nameLabel);
            rectPane.setMinWidth(width);

            Label startLabel = new Label(String.valueOf(currentTime));
            startLabel.setStyle("-fx-font-size: 9px;");
            startLabel.setMinWidth(width);
            startLabel.setAlignment(Pos.CENTER_LEFT);

            VBox cell = new VBox(2, rectPane, startLabel);
            box.getChildren().add(cell);

            currentTime += p.getBurstTime();
        }

        Label endLabel = new Label(String.valueOf(currentTime));
        endLabel.setStyle("-fx-font-size: 9px;");
        box.getChildren().add(new VBox(new javafx.scene.shape.Rectangle(0, 40,
                javafx.scene.paint.Color.TRANSPARENT), endLabel));
    }

    private TableView<MyProcess> createResultsTable() {
        TableView<MyProcess> t = new TableView<>();
        t.getColumns().add(buildCol("ID", "name"));
        t.getColumns().add(buildCol("Arrival", "arrivalTime"));
        t.getColumns().add(buildCol("Burst", "burstTime"));
        t.getColumns().add(buildCol("WT", "waitingTime"));
        t.getColumns().add(buildCol("TAT", "turnAroundTime"));
        t.getColumns().add(buildCol("RT", "responseTime"));
        t.setPrefHeight(180);
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return t;
    }

    private <T> TableColumn<MyProcess, T> buildCol(String title, String prop) {
        TableColumn<MyProcess, T> col = new TableColumn<>(title);
        col.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>(prop));
        return col;
    }

    private void generateAnalysis(List<MyProcess> rr, List<MyProcess> sjf, int q) {
        double avgWTRR  = rr.stream().mapToDouble(MyProcess::getWaitingTime).average().orElse(0);
        double avgTATRR = rr.stream().mapToDouble(MyProcess::getTurnAroundTime).average().orElse(0);
        double avgRTRR  = rr.stream().mapToDouble(MyProcess::getResponseTime).average().orElse(0);

        double avgWTSJF  = sjf.stream().mapToDouble(MyProcess::getWaitingTime).average().orElse(0);
        double avgTATSJF = sjf.stream().mapToDouble(MyProcess::getTurnAroundTime).average().orElse(0);
        double avgRTSJF  = sjf.stream().mapToDouble(MyProcess::getResponseTime).average().orElse(0);

        String sjfType = isPreemptive ? "SJF Preemptive" : "SJF Non-Preemptive";

        StringBuilder sb = new StringBuilder();
        sb.append("========== REQUIRED ANALYSIS & QUESTIONS ==========\n\n");

        sb.append("1. Which algorithm gave lower average waiting time?\n");
        if (avgWTSJF < avgWTRR) {
            sb.append("   - Answer: ").append(sjfType).append(" (Avg WT: ")
              .append(String.format("%.2f", avgWTSJF))
              .append(") performed better than RR (Avg WT: ")
              .append(String.format("%.2f", avgWTRR)).append(").\n\n");
        } else {
            sb.append("   - Answer: Round Robin performed better in this specific workload.\n\n");
        }

        sb.append("2. Which algorithm gave lower average response time?\n");
        if (avgRTRR <= avgRTSJF) {
            sb.append("   - Answer: Round Robin (Avg RT: ").append(String.format("%.2f", avgRTRR))
              .append(") provided faster initial response to processes.\n\n");
        } else {
            sb.append("   - Answer: ").append(sjfType).append(" gave lower response time.\n\n");
        }

        sb.append("3. Fairness & Short Jobs:\n");
        sb.append("   - Round Robin is more FAIR: distributes CPU time equally via time-slicing.\n");
        sb.append("   - SJF is more EFFICIENT: prioritizes short jobs, reducing overall waiting.\n\n");

        sb.append("4. Effect of Time Quantum (").append(q).append("):\n");
        sb.append("   - Small quantum  → better response time but more context switching.\n");
        sb.append("   - Large quantum  → RR behaves like FCFS, long jobs block short ones.\n\n");

        sb.append("5. Recommendation:\n");
        if (avgWTSJF < avgWTRR) {
            sb.append("   - For Efficiency: ").append(sjfType).append(" (minimized total waiting time).\n");
        }
        sb.append("   - For Interactive Systems: Round Robin (guaranteed response for all).\n\n");

        sb.append("========== REQUIRED CONCLUSION ==========\n");
        sb.append("- SJF is more efficient for batch processing.\n");
        sb.append("- Round Robin is fairer for multi-user/interactive environments.\n");
        sb.append("- RR  → Avg WT: ").append(String.format("%.2f", avgWTRR))
          .append(", Avg TAT: ").append(String.format("%.2f", avgTATRR)).append("\n");
        sb.append("- ").append(sjfType).append(" → Avg WT: ")
          .append(String.format("%.2f", avgWTSJF))
          .append(", Avg TAT: ").append(String.format("%.2f", avgTATSJF));

        conclusionArea.setText(sb.toString());
    }

    private List<MyProcess> cloneList(List<MyProcess> original) {
        List<MyProcess> clone = new ArrayList<>();
        for (MyProcess p : original) {
            clone.add(new MyProcess(p.getName(), p.getArrivalTime(), p.getBurstTime()));
        }
        return clone;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}