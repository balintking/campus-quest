import java.util.Scanner;

public class Skeleton {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("válassz egy szekvenciát az alábbiak közül:\n"+
        "1.eset: tárgy felvétele hallgató által\n"+
        "2.eset: tárgy felvétele oktató által\n1"
        );
        while (!scanner.hasNextInt()) {
            System.out.println("hiba");
            scanner.next();
        }
        int number = scanner.nextInt();
        System.out.println("a szam: " + number);
        scanner.close();

        switch(number){
            case 1:
                System.out.println("A bekért szám 1.");
                break;
            case 2:
                System.out.println("A bekért szám 2.");
                break;
            case 3:
                System.out.println("A bekért szám 3.");
                break;
            case 4:
                System.out.println("A bekért szám 4.");
                break;
            case 5:
                System.out.println("A bekért szám 5.");
                break;
            case 6:
                System.out.println("A bekért szám 6.");
                break;
            case 7:
                System.out.println("A bekért szám 7.");
                break;
            case 8:
                System.out.println("A bekért szám 8.");
                break;
            case 9:
                System.out.println("A bekért szám 9.");
                break;
            case 10:
                System.out.println("A bekért szám 10.");
                break;
            case 11:
                System.out.println("A bekért szám 11.");
                break;
            case 12:
                System.out.println("A bekért szám 12.");
                break;
            case 13:
                System.out.println("A bekért szám 13.");
                break;
            case 14:
                System.out.println("A bekért szám 14.");
                break;
            case 15:
                System.out.println("A bekért szám 15.");
                break;
            case 16:
                System.out.println("A bekért szám 16.");
                break;
            case 17:
                System.out.println("A bekért szám 17.");
                break;
            case 18:
                System.out.println("A bekért szám 18.");
                break;
            case 19:
                System.out.println("A bekért szám 19.");
                break;
            case 20:
                System.out.println("A bekért szám 20.");
                break;
            case 21:
                System.out.println("A bekért szám 21.");
                break;
            case 22:
                System.out.println("A bekért szám 22.");
                break;
            case 23:
                System.out.println("A bekért szám 23.");
                break;
            case 24:
                System.out.println("A bekért szám 24.");
                break;
            case 25:
                System.out.println("A bekért szám 25.");
                break;
            default:
                System.out.println("A bekért szám nem esik 1 és 25 közé.");
        }
    }
}

