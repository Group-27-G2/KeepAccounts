import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdvicePanel extends JPanel implements MainGUI.RefreshablePanel {
    private JTextArea adviceArea;
    private JButton getAdviceButton;
    private TransactionManager transactionManager;

    private static final String API_KEY = "sk-W0rpStc95T7JVYVwDYc29IyirjtpPPby6SozFMQr17m8KWeo";
    private static final String API_URL = "https://api.suanli.cn/v1/chat/completions";
    private static final String MODEL_NAME = "free:QwQ-32B";

    public AdvicePanel(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        adviceArea = new JTextArea();
        adviceArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);
        adviceArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(adviceArea);

        getAdviceButton = new JButton("Get AI Financial Advice");
        getAdviceButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        getAdviceButton.setBackground(new Color(135, 206, 250));
        getAdviceButton.addActionListener(this::onGetAdviceClicked);

        add(scrollPane, BorderLayout.CENTER);
        add(getAdviceButton, BorderLayout.SOUTH);
    }

    private void onGetAdviceClicked(ActionEvent e) {
        getAdviceButton.setEnabled(false);
        adviceArea.setText("Retrieving advice from AI, please wait...");

        new Thread(() -> {
            String prompt = buildPrompt();
            String advice = getAIAdvice(prompt);

            SwingUtilities.invokeLater(() -> {
                adviceArea.setText(advice);
                getAdviceButton.setEnabled(true);
            });
        }).start();
    }

    private String buildPrompt() {
        List<Transaction> transactions = transactionManager.getTransactions();
        if (transactions.isEmpty()) {
            return "I currently have no transaction records. How should I start managing my personal finances?";
        }

        StringBuilder sb = new StringBuilder(
                "Please analyze the following transactions and give some financial advice:\n");
        for (Transaction t : transactions) {
            sb.append(String.format("Category: %s, Amount: %.2f, Type: %s, Date: %s, Note: %s\n",
                    t.getCategory(), t.getAmount(), t.getType(), t.getDate(), t.getDescription()));
        }
        return sb.toString();
    }

    private String getAIAdvice(String prompt) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Build JSON safely
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject body = new JSONObject();
            body.put("model", MODEL_NAME);
            body.put("messages", messages);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(body.toString().getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Scanner scanner = new Scanner(conn.getInputStream()).useDelimiter("\\A");
                String response = scanner.hasNext() ? scanner.next() : "";
                scanner.close();
                return extractReply(response);
            } else {
                Scanner scanner = new Scanner(conn.getErrorStream()).useDelimiter("\\A");
                String error = scanner.hasNext() ? scanner.next() : "No error message.";
                scanner.close();
                return "Request failed. HTTP Status: " + responseCode + "\nError: " + error;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error calling AI API: " + ex.getMessage();
        }
    }

    private String extractReply(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray choices = obj.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject message = choices.getJSONObject(0).getJSONObject("message");
                String content = message.getString("content");

                content = content.replaceAll("(?s)<think>.*?</think>", "").trim();

                return content;
            } else {
                return "No advice returned.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to parse AI response: " + e.getMessage();
        }
    }

    @Override
    public void refreshData() {
        // Optional: refresh on switch
    }
}
