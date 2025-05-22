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
    private JLabel statusLabel;

    private static final String API_KEY = "sk-W0rpStc95T7JVYVwDYc29IyirjtpPPby6SozFMQr17m8KWeo";
    private static final String API_URL = "https://api.suanli.cn/v1/chat/completions";
    private static final String MODEL_NAME = "free:QwQ-32B";

    // UI Colors
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color BUTTON_COLOR = new Color(135, 206, 250);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245, 180); // Semi-transparent
    private static final Color TEXT_COLOR = new Color(50, 50, 50);

    public AdvicePanel(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(15, 15));
        setOpaque(false); // Make panel transparent
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("AI Financial Advisor");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel = new JLabel("Ready to generate advice");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(statusLabel, BorderLayout.SOUTH);

        // Advice Display Area with semi-transparent background
        adviceArea = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw semi-transparent background
                g2.setColor(BACKGROUND_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                super.paintComponent(g);
            }
        };

        adviceArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        adviceArea.setLineWrap(true);
        adviceArea.setWrapStyleWord(true);
        adviceArea.setEditable(false);
        adviceArea.setOpaque(false); // Make text area transparent
        adviceArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JScrollPane scrollPane = new JScrollPane(adviceArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setOpaque(false);

        getAdviceButton = new JButton("Get AI Financial Advice");
        getAdviceButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        getAdviceButton.setForeground(Color.WHITE);
        getAdviceButton.setBackground(BUTTON_COLOR);
        getAdviceButton.setFocusPainted(false);
        getAdviceButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                BorderFactory.createEmptyBorder(10, 30, 10, 30)));
        getAdviceButton.addActionListener(this::onGetAdviceClicked);

        // Add rounded corners to button
        getAdviceButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton b = (JButton) c;
                ButtonModel model = b.getModel();

                Color color = BUTTON_COLOR;
                if (model.isPressed()) {
                    color = color.darker();
                } else if (model.isRollover()) {
                    color = color.brighter();
                }

                g2.setColor(color);
                g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);

                super.paint(g2, c);
                g2.dispose();
            }
        });

        buttonPanel.add(getAdviceButton);

        // Assemble components
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onGetAdviceClicked(ActionEvent e) {
        getAdviceButton.setEnabled(false);
        statusLabel.setText("Generating advice...");
        adviceArea.setText("Retrieving advice from AI, please wait...");

        new Thread(() -> {
            String prompt = buildPrompt();
            String advice = getAIAdvice(prompt);

            SwingUtilities.invokeLater(() -> {
                adviceArea.setText(advice);
                getAdviceButton.setEnabled(true);
                statusLabel.setText("Advice generated");
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

                // 🔧 Remove model "thinking" tags (e.g., <think>...</think>)
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