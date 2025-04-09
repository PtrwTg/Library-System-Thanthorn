import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class LibraryGUI extends JFrame {
    private LibraryManager library;
    private JTextArea displayArea;

    public LibraryGUI() {
        try {
            library = new LibraryManager();
        } catch (IOException e) {
            showError("Failed to load data: " + e.getMessage());
            return;
        }

        setTitle("📚 Library Management System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JButton addBookBtn = new JButton("Add Book");
        JButton addMemberBtn = new JButton("Add Member");
        JButton viewBooksBtn = new JButton("View All Books");
        JButton viewMembersBtn = new JButton("View All Members");
        JButton borrowBookBtn = new JButton("Borrow Book");
        JButton returnBookBtn = new JButton("Return Book");
        JButton clearBtn = new JButton("Clear Display");
        JButton exitBtn = new JButton("Exit");

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(displayArea);

        addBookBtn.addActionListener(e -> addBook());
        addMemberBtn.addActionListener(e -> addMember());
        viewBooksBtn.addActionListener(e -> viewBooks());
        viewMembersBtn.addActionListener(e -> viewMembers());
        borrowBookBtn.addActionListener(e -> borrowBook());
        returnBookBtn.addActionListener(e -> returnBook());
        clearBtn.addActionListener(e -> displayArea.setText(""));
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(addBookBtn);
        buttonPanel.add(addMemberBtn);
        buttonPanel.add(viewBooksBtn);
        buttonPanel.add(viewMembersBtn);
        buttonPanel.add(borrowBookBtn);
        buttonPanel.add(returnBookBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addBook() {
        String title = JOptionPane.showInputDialog(this, "Enter book title:");
        if (title != null && !title.isBlank()) {
            try {
                library.addBook(title);
                appendDisplay("✅ Book added: " + title);
            } catch (IOException e) {
                showError("Error saving book: " + e.getMessage());
            }
        }
    }

    private void addMember() {
        String name = JOptionPane.showInputDialog(this, "Enter member name:");
        if (name != null && !name.isBlank()) {
            try {
                library.addMember(name);
                appendDisplay("✅ Member added: " + name);
            } catch (IOException e) {
                showError("Error saving member: " + e.getMessage());
            }
        }
    }

    private void viewBooks() {
        List<Book> books = library.getBooks();
        StringBuilder sb = new StringBuilder("\n📚 All Books:\n");
        for (Book b : books) {
            sb.append(b.getBookId())
              .append(" - ")
              .append(b.getTitle())
              .append(" [")
              .append(b.isAvailable() ? "Available" : "Borrowed")
              .append("]\n");
        }
        displayArea.setText(sb.toString());
    }

    private void viewMembers() {
        List<Member> members = library.getMembers();
        StringBuilder sb = new StringBuilder("\n👥 All Members:\n");
        for (Member m : members) {
            sb.append(m.getMemberId())
              .append(" - ")
              .append(m.getName())
              .append(" | Borrowed Books: ")
              .append(m.getBorrowedBooks())
              .append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void borrowBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter book ID to borrow:");
        String memberId = JOptionPane.showInputDialog(this, "Enter member ID:");
        if (bookId != null && memberId != null && !bookId.isBlank() && !memberId.isBlank()) {
            try {
                library.borrowBook(bookId, memberId);
                appendDisplay("✅ Book borrowed successfully.");
            } catch (IOException e) {
                showError("Error borrowing book: " + e.getMessage());
            }
        }
    }

    private void returnBook() {
        String bookId = JOptionPane.showInputDialog(this, "Enter book ID to return:");
        if (bookId != null && !bookId.isBlank()) {
            try {
                library.returnBook(bookId);
                appendDisplay("✅ Book returned successfully.");
            } catch (IOException e) {
                showError("Error returning book: " + e.getMessage());
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void appendDisplay(String text) {
        displayArea.append(text + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryGUI::new);
    }
}
