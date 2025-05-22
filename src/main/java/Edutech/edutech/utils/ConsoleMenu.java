package Edutech.edutech.utils;

import Edutech.edutech.models.User;
import Edutech.edutech.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu implements CommandLineRunner {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        displayMenu();
    }

    private void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== MENÚ USUARIOS ===");
            System.out.println("1. Listar todos los usuarios");
            System.out.println("2. Crear nuevo usuario");
            System.out.println("3. Buscar usuario por ID");
            System.out.println("4. Actualizar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    listUsers();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    getUserById();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    running = false;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private void listUsers() {
        System.out.println("\nListado de usuarios:");
        userService.getAllUsers().forEach(System.out::println);
    }

    private void createUser() {
        User user = new User();
        System.out.println("\nCrear nuevo usuario:");
        System.out.print("Username: ");
        user.setUsername(scanner.nextLine());
        System.out.print("Email: ");
        user.setEmail(scanner.nextLine());
        System.out.print("Password: ");
        user.setPassword(scanner.nextLine());
        System.out.print("Role (ADMIN/INSTRUCTOR/STUDENT/SUPPORT): ");
        user.setRole(scanner.nextLine().toUpperCase());

        User createdUser = userService.createUser(user);
        System.out.println("Usuario creado con éxito: " + createdUser);
    }

    private void getUserById() {
        System.out.print("\nIngrese ID del usuario a buscar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            User user = userService.getUserById(id);
            System.out.println("Usuario encontrado: " + user);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateUser() {
        System.out.print("\nIngrese ID del usuario a actualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            User existingUser = userService.getUserById(id);
            System.out.println("Usuario actual: " + existingUser);

            User userDetails = new User();
            System.out.print("Nuevo username (dejar vacío para no cambiar): ");
            String username = scanner.nextLine();
            if (!username.isEmpty()) userDetails.setUsername(username);

            System.out.print("Nuevo email (dejar vacío para no cambiar): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) userDetails.setEmail(email);

            System.out.print("Nuevo role (dejar vacío para no cambiar): ");
            String role = scanner.nextLine();
            if (!role.isEmpty()) userDetails.setRole(role.toUpperCase());

            User updatedUser = userService.updateUser(id, userDetails);
            System.out.println("Usuario actualizado: " + updatedUser);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteUser() {
        System.out.print("\nIngrese ID del usuario a eliminar: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            userService.deleteUser(id);
            System.out.println("Usuario eliminado con éxito");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}