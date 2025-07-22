import java.io.*;

public class User {
 private String workerID;
    private String email;
    private String password;

    public User(String workerID, String email, String password) {                              //constructor of user
        this.workerID = workerID;
        this.email = email;
        this.password = password;
    }

    public String getWorkerID() {                                                              //return workerID
        return workerID;
    }

    public String getEmail() {                                                                 //return email
        return email;
    }

    public String getPassword() {                                                              //return password value
        return password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    
    public void saveToFile() throws IOException {                                               // Save user information to a filev
        BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));          //You can write in batches instead of writing characters to the file one by one
        writer.write(workerID + "," + email + "," + password + "\n");
        writer.close();
    }

    
    public void updateToFile() throws IOException {                                             // Update user information in the file
        File inputFile = new File("users.txt");
        File tempFile = new File("temp_users.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");                                                 // use , to split the information
            if (userData[1].equals(this.email)) {
                writer.write(this.workerID + "," + this.email + "," + this.password + "\n");
            } else {
                writer.write(line + "\n");
            }
        }
        writer.close();                                                                          // close function
        reader.close();                                                                          // close function

        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

                                                                                                 
    public static User findByWorkerID(String workerID) throws IOException {                      // Static method to find user by workerID
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
                                                                                                 //You can write in batches instead of writing characters to the file one by one
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[0].equals(workerID)) {
                reader.close();
                return new User(userData[0], userData[1], userData[2]);
            }
        }
        reader.close();
        return null;
    }

    
    public static User findByEmail(String email) throws IOException {                             // Static method to find user by email
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[1].equals(email)) {
                reader.close();
                return new User(userData[0], userData[1], userData[2]);
            }
        }
        reader.close();                                                                            // close function
        return null;
    }
}
