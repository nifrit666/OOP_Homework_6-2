package notebook.ui;

import notebook.core.controller.Commands;

import java.util.Scanner;

public interface View {

    void run();

    default void showMenu(String header) {
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        for (var item : Commands.values()) {
            if (item != Commands.NONE) {
                sb.append(" ").append(item).append("\n");
            }
        }
        sb.append("-----------------------------------------");
        System.out.print("\033[H\033[J");
        System.out.println(sb);
    }
    default String prompt(String message){
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
    default void timeOut() {
        System.out.println("Press ENTER to continue");
        Scanner stopScn = new Scanner(System.in);
        stopScn.nextLine();
    }
}
