import java.io.IOException;
import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) throws IOException {
        LibraryManager library = new LibraryManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. View All Books");
            System.out.println("4. View All Members");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("📖 Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    library.addBook(bookTitle);
                    break;

                case 2:
                    System.out.print("👤 Enter member name: ");
                    String memberName = scanner.nextLine();
                    library.addMember(memberName);
                    break;

                case 3:
                    library.displayBooks();
                    break;

                case 4:
                    library.displayMembers();
                    break;

                case 5:
                    System.out.print("📖 Enter book ID to borrow: ");
                    String borrowBookId = scanner.nextLine();
                    System.out.print("👤 Enter member ID: ");
                    String borrowerId = scanner.nextLine();
                    library.borrowBook(borrowBookId, borrowerId);
                    break;

                case 6:
                    System.out.print("📖 Enter book ID to return: ");
                    String returnBookId = scanner.nextLine();
                    library.returnBook(returnBookId);
                    break;

                case 7:
                    System.out.println("👋 Exiting the system...");
                    return;

                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
        }
    }
}
