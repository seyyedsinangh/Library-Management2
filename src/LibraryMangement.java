import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Collections;
import java.util.Collection;
import java.util.Scanner;
import java.util.Date;
import java.util.Objects;

public class LibraryMangement {
    public static void main(String[] args) {
        Parser.commandProcessor();
    }
}


class Parser {
    private static SystemManager systemManager;

    public static void commandProcessor() {
        systemManager = new SystemManager();
        String command;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            command = scanner.nextLine();
            if (command.equals("finish")) break;
            String[] splitted = command.split("#");
            switch (splitted[0]) {
                case "add-library":
                    addLibrary(splitted[1]);
                    break;
                case "add-category":
                    addCategory(splitted[1]);
                    break;
                case "add-student":
                    addStudent(splitted[1]);
                    break;
                case "add-staff":
                    addStaff(splitted[1]);
                    break;
                case "add-manager":
                    addManager(splitted[1]);
                    break;
                case "add-book":
                    addBook(splitted[1]);
                    break;
                case "add-thesis":
                    addThesis(splitted[1]);
                    break;
                case "add-ganjineh-book":
                    addGanjineh(splitted[1]);
                    break;
                case "add-selling-book":
                    addSellingBook(splitted[1]);
                    break;
                case "remove-user":
                    removeUser(splitted[1]);
                    break;
                case "remove-resource":
                    removeResource(splitted[1]);
                    break;
                case "borrow":
                    borrow(splitted[1]);
                    break;
                case "return":
                    returning(splitted[1]);
                    break;
                case "buy":
                    buy(splitted[1]);
                    break;
                case "read":
                    read(splitted[1]);
                    break;
                case "add-comment":
                    addComment(splitted[1]);
                    break;
                case "search":
                    search(splitted[1].toLowerCase());
                    break;
                case "search-user":
                    searchUser(splitted[1]);
                    break;
                case "category-report":
                    categoryReport(splitted[1]);
                    break;
                case "library-report":
                    libraryReport(splitted[1]);
                    break;
                case "report-passed-deadline":
                    reportPassedDeadline(splitted[1]);
                    break;
                case "report-penalties-sum":
                    reportPenaltiesSum(splitted[1]);
                    break;
                default:
                    break;
            }
        }
    }

    private static void addLibrary(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (systemManager.searchLibrary(splitted[2])) {
            System.out.println("duplicate-id");
        } else {
            systemManager.putLibrary(new Library(splitted[2],splitted[3],splitted[4],
                    Integer.parseInt(splitted[5]),splitted[6]));
            System.out.println("success");
        }
    }

    private static void addCategory(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (systemManager.searchCategory(splitted[2])) {
            System.out.println("duplicate-id");
        } else if ((!splitted[4].equals("null")) && (!systemManager.searchCategory(splitted[4]))) {
            System.out.println("not-found");
        } else {
            systemManager.putCategory(new Category(splitted[2],splitted[3]),splitted[4]);
            System.out.println("success");
        }
    }

    private static void addStudent(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (systemManager.searchUser(splitted[2])) {
            System.out.println("duplicate-id");
        } else {
            systemManager.putUser(new Student(splitted[2],splitted[3],splitted[4],
                    splitted[5],splitted[6],splitted[7],splitted[8]));
            System.out.println("success");
        }
    }

    private static void addStaff(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (systemManager.searchUser(splitted[2])) {
            System.out.println("duplicate-id");
        } else {
            if (splitted[9].equals("staff")) {
                systemManager.putUser(new Staff(splitted[2], splitted[3], splitted[4],
                        splitted[5], splitted[6], splitted[7], splitted[8]));
            } else {
                systemManager.putUser(new Professor(splitted[2], splitted[3], splitted[4],
                        splitted[5], splitted[6], splitted[7], splitted[8]));
            }
            System.out.println("success");
        }
    }

    private static void addManager(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (systemManager.searchUser(splitted[2])) {
            System.out.println("duplicate-id");
        } else if (!systemManager.searchLibrary(splitted[9])) {
            System.out.println("not-found");
        } else {
            systemManager.putManager(new Manager(splitted[2],splitted[3],splitted[4],
                    splitted[5],splitted[6],splitted[7],splitted[8],splitted[9]));
            System.out.println("success");
        }
    }

    private static void addBook(String command) {
        String[] splitted = command.split("\\|");
        if (checkAddResource(splitted[9],splitted[8],splitted[2],splitted[0],splitted[1]));
        else {
            systemManager.putResource(splitted[9],new BorrowableBook(splitted[2],splitted[3],
                    splitted[8],splitted[9],splitted[4],splitted[5],splitted[6],Integer.parseInt(splitted[7])));
            System.out.println("success");
        }
    }

    private static void addThesis(String command) {
        String[] splitted = command.split("\\|");
        if (checkAddResource(splitted[8],splitted[7],splitted[2],splitted[0],splitted[1]));
        else {
            systemManager.putResource(splitted[8],new Thesis(splitted[2],splitted[3],
                    splitted[7],splitted[8],splitted[4],splitted[5],splitted[6],1));
            System.out.println("success");
        }
    }

    private static void addGanjineh(String command) {
        String[] splitted = command.split("\\|");
        if (checkAddResource(splitted[9],splitted[8],splitted[2],splitted[0],splitted[1]));
        else {
            systemManager.putResource(splitted[9],new GanjinehBook(splitted[2],splitted[3],
                    splitted[8],splitted[9],splitted[4],splitted[5],splitted[6],1,splitted[7]));
            System.out.println("success");
        }
    }

    private static void addSellingBook(String command) {
        String[] splitted = command.split("\\|");
        if (checkAddResource(splitted[11],splitted[10],splitted[2],splitted[0],splitted[1]));
        else {
            systemManager.putResource(splitted[11],new SellingBook(splitted[2],splitted[3],
                    splitted[10],splitted[11],splitted[4],splitted[5],splitted[6],Integer.parseInt(splitted[7]),
                    Long.parseLong(splitted[8]),Double.parseDouble(splitted[9])));
            System.out.println("success");
        }
    }

    private static void removeUser(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else if (!systemManager.searchUser(splitted[2])) {
            System.out.println("not-found");
        } else if (!systemManager.isUserRemovalAllowed(splitted[2])) {
            System.out.println("not-allowed");
        } else {
            systemManager.removeUser(splitted[2]);
            System.out.println("success");
        }
    }

    private static void removeResource(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (checkLibResource(splitted[3],splitted[2]));
        else if (!systemManager.checkManagerPermission(splitted[0],splitted[3])) {
            System.out.println("permission-denied");
        } else if (!systemManager.isResourceRemovalAllowed(splitted[3],splitted[2])) {
            System.out.println("not-allowed");
        } else {
            systemManager.removeResource(splitted[3],splitted[2]);
            System.out.println("success");
        }
    }

    private static void borrow(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (checkLibResource(splitted[2],splitted[3]));
        else if (!systemManager.isBorrowAllowed(splitted[0],splitted[2],splitted[3],
                new DateMaker(splitted[4],splitted[5]))) {
            System.out.println("not-allowed");
        } else {
            systemManager.putBorrowed(new Borrowed(splitted[0],splitted[2],
                    splitted[3],new DateMaker(splitted[4],splitted[5])));
            System.out.println("success");
        }
    }

    private static void returning(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (!systemManager.searchBorrowed(splitted[0],splitted[2],splitted[3])) {
            System.out.println("not-found");
        } else {
            long penalty = systemManager.returning(splitted[0],splitted[2],
                    splitted[3],new DateMaker(splitted[4],splitted[5]));
            if (penalty==0) {
                System.out.println("success");
            } else {
                System.out.println(penalty);
            }
        }
    }

    private static void buy(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (systemManager.isManager(splitted[0])) {
            System.out.println("permission-denied");
        } else if (checkLibResource(splitted[2],splitted[3]));
        else if (!systemManager.isSellingAllowed(splitted[0],splitted[2],splitted[3])) {
            System.out.println("not-allowed");
        } else {
            systemManager.sell(splitted[2],splitted[3]);
            System.out.println("success");
        }
    }

    private static void read(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (!systemManager.isProfessor(splitted[0])) {
            System.out.println("permission-denied");
        } else if (checkLibResource(splitted[2],splitted[3]));
        else if (!systemManager.isReadAllowed(splitted[0],splitted[2],
                splitted[3],new DateMaker(splitted[4],splitted[5]))) {
            System.out.println("not-allowed");
        } else {
            systemManager.read(splitted[2],splitted[3],new DateMaker(splitted[4],splitted[5]));
            System.out.println("success");
        }
    }

    private static void addComment(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (!systemManager.isCommenter(splitted[0])) {
            System.out.println("permission-denied");
        } else if (checkLibResource(splitted[2],splitted[3]));
        else {
            systemManager.putComment(splitted[0],splitted[2],splitted[3],splitted[4]);
            System.out.println("success");
        }
    }

    private static void search(String word) {
        ArrayList<String> result = systemManager.search(word);
        if (result.isEmpty()) {
            System.out.println("not-found");
        } else {
            Collections.sort(result);
            for (int i = 0; i < result.size()-1; i++) {
                System.out.print(result.get(i) + "|");
            }
            System.out.println(result.get(result.size()-1));
        }
    }

    private static void searchUser(String command) {
        String[] splitted = command.split("\\|");
        if (checkUser(splitted[0],splitted[1]));
        else if (!systemManager.isSearcher(splitted[0])) {
            System.out.println("permission-denied");
        } else {
            ArrayList<String> result = systemManager.searchUser(splitted[0],splitted[2].toLowerCase());
            if (result.isEmpty()) {
                System.out.println("not-found");
            } else {
                Collections.sort(result);
                for (int i = 0; i < result.size()-1; i++) {
                    System.out.print(result.get(i) + "|");
                }
                System.out.println(result.get(result.size()-1));
            }
        }
    }

    private static void categoryReport(String command) {
        String[] splitted = command.split("\\|");
        if (checkReport(splitted[0],splitted[1],splitted[3]));
        else if (!systemManager.searchCategory(splitted[2])) {
            System.out.println("not-found");
        } else {
            System.out.println(systemManager.categoryReport(splitted[3],splitted[2]));
        }
    }

    private static void libraryReport(String command) {
        String[] splitted = command.split("\\|");
        if (checkReport(splitted[0],splitted[1],splitted[2]));
        else {
            System.out.println(systemManager.libraryReport(splitted[2]));
        }
    }

    private static void reportPassedDeadline(String command) {
        String[] splitted = command.split("\\|");
        if (checkReport(splitted[0],splitted[1],splitted[2]));
        else {
            System.out.println(systemManager.reportPassedDeadline(splitted[2],new DateMaker(splitted[3],splitted[4])));
        }
    }

    private static void reportPenaltiesSum(String command) {
        String[] splitted = command.split("\\|");
        if (checkAdmin(splitted[0],splitted[1]));
        else {
            System.out.println(systemManager.reportPenaltiesSum());
        }
    }

    private static boolean checkAdmin(String adminId,String adminPass) {
        if (systemManager.searchUser(adminId)) {
            System.out.println("permission-denied");
            return true;
        } else if (!systemManager.checkAdminId(adminId)) {
            System.out.println("not-found");
            return true;
        } else if (!systemManager.checkAdminPass(adminPass)) {
            System.out.println("invalid-pass");
            return true;
        }
        return false;
    }

    private static boolean checkUser(String userId,String pass) {
        if (!systemManager.searchUser(userId)) {
            System.out.println("not-found");
            return true;
        } else if (!systemManager.checkUserPass(userId,pass)) {
            System.out.println("invalid-pass");
            return true;
        }
        return false;
    }

    private static boolean checkAddResource(String libId, String catId, String resourceId,
                                            String managerId, String managerPass) {
        if (checkUser(managerId,managerPass)) return true;
        else if (!systemManager.searchLibrary(libId)) {
            System.out.println("not-found");
            return true;
        } else if (!systemManager.checkManagerPermission(managerId,libId)) {
            System.out.println("permission-denied");
            return true;
        } else if (systemManager.searchResource(resourceId,libId)) {
            System.out.println("duplicate-id");
            return true;
        } else if (!systemManager.searchCategory(catId)) {
            System.out.println("not-found");
            return true;
        }
        return false;
    }

    private static boolean checkLibResource(String libId,String resourceId) {
        if (!systemManager.searchLibrary(libId)) {
            System.out.println("not-found");
            return true;
        } else if (!systemManager.searchResource(resourceId,libId)) {
            System.out.println("not-found");
            return true;
        }
        return false;
    }

    private static boolean checkReport(String userId,String pass,String libId) {
        if (checkUser(userId,pass)) return true;
        else if (!systemManager.searchLibrary(libId)) {
            System.out.println("not-found");
            return true;
        } else if (!systemManager.checkManagerPermission(userId,libId)) {
            System.out.println("permission-denied");
            return true;
        }
        return false;
    }

}


class SystemManager {
    private HashMap<String,Library> libraries;
    private HashMap<String,Category> categories;
    private HashMap<String,User> users;
    private HashMap<String,HashSet<Borrowed>> borroweds;
    private User admin;

    public SystemManager() {
        this.libraries = new HashMap<>();
        this.categories = new HashMap<>();
        Category category = new Category("null","null");
        categories.put("null",category);
        this.borroweds = new HashMap<>();
        this.users = new HashMap<>();
        this.admin = new User("admin","AdminPass","admin",
                "admin","admin","admin","admin");
    }

    public boolean searchLibrary(String id) {
        return libraries.containsKey(id);
    }

    public boolean searchCategory(String id) {
        return categories.containsKey(id);
    }

    public boolean searchUser(String id) {
        return users.containsKey(id);
    }

    public boolean searchResource(String resourceId,String libId) {
        return libraries.get(libId).searchResource(resourceId);
    }

    public boolean searchBorrowed(String userId,String libId,String resourceId) {
        for (Borrowed borrowed: borroweds.get(userId)) {
            if (borrowed.getLibraryId().equals(libId) && borrowed.getResourceId().equals(resourceId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isManager(String userId) {
        return users.get(userId) instanceof Manager;
    }

    public boolean isProfessor(String userId) {
        return users.get(userId) instanceof Professor;
    }

    public boolean isUserRemovalAllowed(String userId) {
        if (!borroweds.get(userId).isEmpty()) return false;
        if (users.get(userId).getPenalty()>0) return false;
        return true;
    }

    public boolean isResourceRemovalAllowed(String libId,String resourceId) {
        if (!borroweds.get(libId + resourceId).isEmpty()) return false;
        else return true;
    }

    public boolean isBorrowAllowed(String userId, String libId, String resourceId, DateMaker dateTime) {
        if (!libraries.get(libId).isBorrowAllowed(resourceId)) return false;
        if (users.get(userId) instanceof Student ?
                borroweds.get(userId).size()==3 : borroweds.get(userId).size()==5) {
            return false;
        }
        for (Borrowed borrowed: borroweds.get(userId)) {
            if (borrowed.getResourceId().equals(resourceId) && borrowed.getLibraryId().equals(libId)) return false;
        }
        for (Borrowed borrowed: borroweds.get(userId)) {
            if (calculatePenalty(borrowed,dateTime) > 0) return false;
        }
        return true;
    }

    public boolean isSellingAllowed(String userId,String libId,String resourceId) {
        if (libraries.get(libId).getResourceType(resourceId)!=2) return false;
        if (!libraries.get(libId).isSellable(resourceId)) return false;
        if (users.get(userId).getPenalty()!=0) return false;
        return true;
    }

    public boolean isReadAllowed(String userId,String libId,String resourceId,DateMaker readTime) {
        if (users.get(userId).getPenalty()>0) return false;
        for (Borrowed borrowed: borroweds.get(userId)) {
            if (calculatePenalty(borrowed, readTime)>0) return false;
        }
        if (libraries.get(libId).getResourceType(resourceId)!=3) return false;
        if (!libraries.get(libId).isReadable(resourceId, readTime)) return false;
        return true;
    }

    public boolean isSearcher(String userId) {
        return users.get(userId) instanceof Searcher;
    }

    public boolean isCommenter(String userId) {
        return users.get(userId) instanceof Commenter;
    }

    public boolean checkAdminId(String id) {
        return admin.getUserId().equals(id);
    }

    public boolean checkAdminPass(String pass) {
        return admin.getPassword().equals(pass);
    }

    public boolean checkUserPass(String userId,String userPass) {
        return users.get(userId).getPassword().equals(userPass);
    }

    public boolean checkManagerPermission(String userId,String libId) {
        if (isManager(userId)) {
            Manager manager = (Manager) users.get(userId);
            return manager.getLibraryId().equals(libId);
        } else {
            return false;
        }
    }

    private long calculatePenalty(Borrowed borrowed,DateMaker dateMaker) {
        long stuBook = 10*24*60;
        long stuThes = 7*24*60;
        long stafBook = 14*24*60;
        long stafThes = 10*24*60;
        long difference = dateMaker.toMinute() - DateMaker.toMinute(borrowed.getDateTime());
        int resourceType = libraries.get(borrowed.getLibraryId()).getResourceType(borrowed.getResourceId());
        long penalty = users.get(borrowed.getUserId()) instanceof Student ?
                (resourceType==1 ? (difference-stuBook)/60*50 : (difference-stuThes)/60*50) :
                (resourceType==1 ? (difference-stafBook)/60*100 : (difference-stafThes)/60*100);
        if (penalty<0) return 0;
        else return penalty;
    }

    public void removeUser(String userId) {
        if (users.get(userId) instanceof Manager) {
            Manager manager = (Manager) users.get(userId);
            libraries.get(manager.getLibraryId()).removeManager(userId);
        }
        borroweds.remove(userId);
        users.remove(userId);
    }

    public void removeResource(String libId,String resourceId) {
        borroweds.remove(libId + resourceId);
        libraries.get(libId).removeResource(resourceId);
    }

    public long returning(String userId,String libId,String resourceId,DateMaker dateMaker) {
        Borrowed returned = null;
        for (Borrowed borrowed: borroweds.get(userId)) {
            if (borrowed.getLibraryId().equals(libId) && borrowed.getResourceId().equals(resourceId)) {
                returned = borrowed;
                break;
            }
        }
        borroweds.get(userId).remove(returned);
        borroweds.get(libId + resourceId).remove(returned);
        borroweds.get(libId).remove(returned);
        libraries.get(libId).returning(resourceId);
        long penalty = calculatePenalty(returned, dateMaker);
        users.get(userId).addPenalty(penalty);
        return penalty;
    }

    public void sell(String libId,String resourceId) {
        libraries.get(libId).sell(resourceId);
    }

    public void read(String libId,String resourceId,DateMaker readTime) {
        libraries.get(libId).setReadTime(resourceId,readTime);
    }

    public ArrayList<String> search(String word) {
        ArrayList<String> result = new ArrayList<>();
        for (Library library: libraries.values()) {
            library.search(result,word);
        }
        return result;
    }

    public ArrayList<String> searchUser(String userId,String word) {
        Searcher searcher = (Searcher) users.get(userId);
        return searcher.searchUser(users.values(),word);
    }

    public String categoryReport(String libId,String categoryId) {
        Library library = libraries.get(libId);
        HashMap<String,Integer> report;
        int book = 0;
        int thesis = 0;
        int ganjineh = 0;
        int selling = 0;
        for (Category category: categories.values()) {
            if (categoryCheckReport(category,categoryId)) {
                report = library.categoryReport(category.getId());
                book += report.get("book");
                thesis += report.get("thesis");
                ganjineh += report.get("ganjineh");
                selling += report.get("selling");
            }
        }
        return book + " " + thesis + " " + ganjineh + " " + selling;
    }

    private boolean categoryCheckReport(Category category,String categoryId) {
        if (category==null) return false;
        else if (category.getId().equals(categoryId)) return true;
        else return categoryCheckReport(category.getFather(),categoryId);
    }

    public String libraryReport(String libId) {
        HashMap<String,Integer> report = libraries.get(libId).libraryReport();
        int book = report.get("book");
        int thesis = report.get("thesis");
        int ganjineh = report.get("ganjineh");
        int selling = report.get("selling");
        int borrowedBook = 0;
        int borrowedThesis = 0;
        for (Borrowed borrowed: borroweds.get(libId)) {
            switch (libraries.get(libId).getResourceType(borrowed.getResourceId())) {
                case 1:
                    borrowedBook++;
                    break;
                case 4:
                    borrowedThesis++;
                    break;
            }
        }
        return book + " " + thesis + " " + borrowedBook + " " + borrowedThesis + " " + ganjineh + " " + selling;
    }

    public String reportPassedDeadline(String libId,DateMaker dateTime) {
        HashSet<String> result = new HashSet<>();
        for (Borrowed borrowed: borroweds.get(libId)) {
            if (calculatePenalty(borrowed,dateTime)>0) {
                result.add(borrowed.getResourceId());
            }
        }
        if (result.isEmpty()) return "none";
        ArrayList<String> res = new ArrayList<>();
        for (String id: result) {
            res.add(id);
        }
        String report = "";
        Collections.sort(res);
        for (int i = 0; i < res.size()-1; i++) {
            report += res.get(i) + "|";
        }
        report += res.get(result.size()-1);
        return report;
    }

    public long reportPenaltiesSum() {
        long sum = 0;
        for (User user: users.values()) {
            sum += user.getPenalty();
        }
        return sum;
    }

    public void putLibrary(Library lib) {
        libraries.put(lib.getId(),lib);
        borroweds.put(lib.getId(),new HashSet<Borrowed>());
    }

    public void putCategory(Category category,String fatherId) {
        if (!fatherId.equals("null")) category.setFather(categories.get(fatherId));
        categories.put(category.getId(),category);
    }

    public void putUser(User user) {
        users.put(user.getUserId(),user);
        borroweds.put(user.getUserId(),new HashSet<Borrowed>());
    }

    public void putManager(Manager manager) {
        libraries.get(manager.getLibraryId()).putManager(manager);
        users.put(manager.getUserId(),manager);
        borroweds.put(manager.getUserId(),new HashSet<Borrowed>());
    }

    public void putResource(String libId,Resource resource) {
        libraries.get(libId).putResource(resource);
        borroweds.put(libId + resource.getId(),new HashSet<Borrowed>());
    }

    public void putBorrowed(Borrowed borrowed) {
        borroweds.get(borrowed.getLibraryId()).add(borrowed);
        borroweds.get(borrowed.getUserId()).add(borrowed);
        borroweds.get(borrowed.getLibraryId() + borrowed.getResourceId()).add(borrowed);
        libraries.get(borrowed.getLibraryId()).borrow(borrowed.getResourceId());
    }

    public void putComment(String userId,String libId,String resourceId,String comment) {
        Commenter user = (Commenter) users.get(userId);
        user.putComment(libraries.get(libId).getResource(resourceId),comment);
    }
}


class Library {
    private String id;
    private String name;
    private String establishYear;
    private int tableCount;
    private String address;
    private HashMap<String,Resource> resources;
    private HashMap<String,Manager> managers;

    public Library(String id, String name, String establishYear, int tableCount,
                   String address) {
        this.id = id;
        this.name = name;
        this.establishYear = establishYear;
        this.tableCount = tableCount;
        this.address = address;
        this.resources = new HashMap<>();
        this.managers = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public Resource getResource(String resourceId) {
        return resources.get(resourceId);
    }

    // 1=BorrowableBook,2=SellingBook,3=GanjinehBook,4=Thesis
    public int getResourceType(String resourceId) {
        Resource resource = resources.get(resourceId);
        return resource instanceof BorrowableBook ?
                1 : (resource instanceof SellingBook ? 2 : (resource instanceof GanjinehBook ? 3 : 4));
    }

    public boolean searchResource(String resourceId) {
        return resources.containsKey(resourceId);
    }

    public boolean isBorrowAllowed(String resourceId) {
        if (!(resources.get(resourceId) instanceof Borrowable)) return false;
        Borrowable borrowable = (Borrowable) resources.get(resourceId);
        if (!borrowable.isBorrowable()) return false;
        return true;
    }

    public boolean isSellable(String resourceId) {
        SellingBook sellingBook = (SellingBook) resources.get(resourceId);
        return sellingBook.isSellable();
    }

    public boolean isReadable(String resourceId,DateMaker readTime) {
        GanjinehBook ganjinehBook = (GanjinehBook) resources.get(resourceId);
        return ganjinehBook.isReadable(readTime);
    }

    public void putManager(Manager manager) {
        managers.put(manager.getUserId(),manager);
    }

    public void putResource(Resource resource) {
        resources.put(resource.getId(),resource);
    }

    public void removeManager(String userId) {
        managers.remove(userId);
    }

    public void removeResource(String resourceId) {
        resources.remove(resourceId);
    }

    public void borrow(String resourceId) {
        Borrowable borrowable = (Borrowable) resources.get(resourceId);
        borrowable.borrow();
    }

    public void returning(String resourceId) {
        Borrowable borrowable = (Borrowable) resources.get(resourceId);
        borrowable.returning();
    }

    public void sell(String resourceId) {
        SellingBook sellingBook = (SellingBook) resources.get(resourceId);
        sellingBook.sell();
    }

    public void search(ArrayList<String> result,String word) {
        for (Resource resource: resources.values()) {
            if (resource.isSearchAcceptable(word)) {
                result.add(resource.getId());
            }
        }
    }

    public HashMap<String,Integer> categoryReport(String categoryId) {
        HashMap<String,Integer> report = new HashMap<>();
        int thesis = 0;
        int book = 0;
        int ganjineh = 0;
        int selling = 0;
        for (Resource resource: resources.values()) {
            if (resource.getCategoryId().equals(categoryId)) {
                switch (getResourceType(resource.getId())) {
                    case 1:
                        book += resource.getCount();
                        break;
                    case 2:
                        selling += resource.getCount();
                        break;
                    case 3:
                        ganjineh++;
                        break;
                    case 4:
                        thesis += resource.getCount();
                        break;
                }
            }
        }
        report.put("thesis",thesis);
        report.put("book",book);
        report.put("ganjineh",ganjineh);
        report.put("selling",selling);
        return report;
    }

    public HashMap<String,Integer> libraryReport() {
        HashMap<String,Integer> report = new HashMap<>();
        int book = 0;
        int thesis = 0;
        int ganjineh = 0;
        int selling = 0;
        for (Resource resource: resources.values()) {
            switch (getResourceType(resource.getId())) {
                case 1:
                    book += resource.getCount();
                    break;
                case 2:
                    selling += resource.getCount();
                    break;
                case 3:
                    ganjineh++;
                    break;
                case 4:
                    thesis += resource.getCount();
                    break;
            }
        }
        report.put("thesis",thesis);
        report.put("book",book);
        report.put("ganjineh",ganjineh);
        report.put("selling",selling);
        return report;
    }

    public void setReadTime(String resourceId,DateMaker readTime) {
        GanjinehBook ganjinehBook = (GanjinehBook) resources.get(resourceId);
        ganjinehBook.setReadTime(readTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(id, library.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


class Category {
    private String id;
    private String name;
    private Category father;

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
        this.father = null;
    }

    public String getId() {
        return id;
    }

    public Category getFather() {
        return father;
    }

    public void setFather(Category father) {
        this.father = father;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


class Borrowed {
    private String userId;
    private String resourceId;
    private String libraryId;
    private Date dateTime;

    public Borrowed(String userId, String libraryId, String resourceId, DateMaker dateMaker) {
        this.userId = userId;
        this.libraryId = libraryId;
        this.resourceId = resourceId;
        this.dateTime = dateMaker.getDateTime();
    }

    public String getUserId() {
        return userId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowed borrowed = (Borrowed) o;
        return Objects.equals(userId, borrowed.userId) && Objects.equals(resourceId, borrowed.resourceId) &&
                Objects.equals(libraryId, borrowed.libraryId) && Objects.equals(dateTime, borrowed.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, resourceId, libraryId, dateTime);
    }
}


class DateMaker {
    private Date dateTime;

    public DateMaker(String date, String time) {
        String[] splitted1 = date.split("-");
        String[] splitted2 = time.split(":");
        this.dateTime = new Date(Integer.parseInt(splitted1[0]),Integer.parseInt(splitted1[1])-1,
                Integer.parseInt(splitted1[2]),Integer.parseInt(splitted2[0]),Integer.parseInt(splitted2[1]));
    }

    public long toMinute() {
        return dateTime.getTime()/(1000*60);
    }

    public static long toMinute(Date dateTime) {
        return dateTime.getTime()/(1000*60);
    }

    public Date getDateTime() {
        return dateTime;
    }
}


class User {
    private String userId;
    private String password;
    private String firstname;
    private String lastname;
    private String nationalId;
    private String birthYear;
    private String address;
    private long penalty;

    public User(String userId, String password, String firstname,
                String lastname, String nationalId, String birthYear, String address) {
        this.userId = userId;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nationalId = nationalId;
        this.birthYear = birthYear;
        this.address = address;
        this.penalty = 0;
    }

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }

    public long getPenalty() {
        return penalty;
    }

    public void addPenalty(long penalty) {
        this.penalty += penalty;
    }

    public boolean isSearchAcceptable(String word) {
        if (firstname.toLowerCase().contains(word) || lastname.toLowerCase().contains(word)) return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}


class Manager extends User implements Searcher {
    private String libraryId;

    public Manager(String userId, String password, String firstname, String lastname,
                   String nationalId, String birthYear, String address, String libraryId) {
        super(userId, password, firstname, lastname, nationalId, birthYear, address);
        this.libraryId = libraryId;
    }

    public String getLibraryId() {
        return libraryId;
    }

    @Override
    public ArrayList<String> searchUser(Collection<User> users, String word) {
        ArrayList<String> result = new ArrayList<>();
        for (User user: users) {
            if (user.isSearchAcceptable(word)) {
                result.add(user.getUserId());
            }
        }
        return result;
    }
}


class Professor extends User implements Commenter,Searcher {
    public Professor(String userId, String password, String firstname, String lastname,
                     String nationalId, String birthYear, String address) {
        super(userId, password, firstname, lastname, nationalId, birthYear, address);
    }

    @Override
    public void putComment(Resource resource, String comment) {
        resource.putComment(getUserId(),comment);
    }

    @Override
    public ArrayList<String> searchUser(Collection<User> users, String word) {
        ArrayList<String> result = new ArrayList<>();
        for (User user: users) {
            if (user.isSearchAcceptable(word)) {
                result.add(user.getUserId());
            }
        }
        return result;
    }
}


class Staff extends User implements Searcher {
    public Staff(String userId, String password, String firstname, String lastname,
                 String nationalId, String birthYear, String address) {
        super(userId, password, firstname, lastname, nationalId, birthYear, address);
    }

    @Override
    public ArrayList<String> searchUser(Collection<User> users, String word) {
        ArrayList<String> result = new ArrayList<>();
        for (User user: users) {
            if (user.isSearchAcceptable(word)) {
                result.add(user.getUserId());
            }
        }
        return result;
    }
}


class Student extends User implements Commenter {

    public Student(String userId, String password, String firstname, String lastname,
                   String nationalId, String birthYear, String address) {
        super(userId, password, firstname, lastname, nationalId, birthYear, address);
    }

    @Override
    public void putComment(Resource resource, String comment) {
        resource.putComment(getUserId(),comment);
    }
}


abstract class Resource {
    private String id;
    private String title;
    private String categoryId;
    private String libraryId;
    protected int count;
    private final int initialCount;
    private HashMap<String,String> comments;

    public Resource(String id, String title, String categoryId, String libraryId, int count) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.libraryId = libraryId;
        this.count = count;
        this.initialCount = count;
        this.comments = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getCount() {
        return count;
    }

    public void putComment(String userId, String comment) {
        comments.put(userId,comment);
    }

    public boolean isSearchAcceptable(String word) {
        if (title.toLowerCase().contains(word)) return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


abstract class Book extends Resource {
    private String writer;
    private String publication;
    private String publishYear;

    public Book(String id, String title, String categoryId, String libraryId,
                String writer, String publication, String publishYear, int count) {
        super(id, title, categoryId, libraryId, count);
        this.writer = writer;
        this.publication = publication;
        this.publishYear = publishYear;
    }

    @Override
    public boolean isSearchAcceptable(String word) {
        if (super.isSearchAcceptable(word) || writer.toLowerCase().contains(word) ||
                publication.toLowerCase().contains(word)) return true;
        else return false;
    }
}


class BorrowableBook extends Book implements Borrowable {

    public BorrowableBook(String id, String title, String categoryId, String libraryId,
                          String writer, String publication, String publishYear, int count) {
        super(id, title, categoryId, libraryId, writer, publication, publishYear, count);
    }

    @Override
    public boolean isBorrowable() {
        if (count == 0) return false;
        else return true;
    }

    @Override
    public void borrow() {
        count--;
    }

    @Override
    public void returning() {
        count++;
    }
}


class GanjinehBook extends Book {
    private String dedicator;
    private DateMaker readTime;

    public GanjinehBook(String id, String title, String categoryId, String libraryId, String writer,
                        String publication, String publishYear, int count, String dedicator) {
        super(id, title, categoryId, libraryId, writer, publication, publishYear, count);
        this.dedicator = dedicator;
        this.readTime = null;
    }

    public boolean isReadable(DateMaker readTime) {
        if (this.readTime==null) return true;
        else if ((readTime.toMinute() - this.readTime.toMinute()) < 120) return false;
        else return true;
    }

    public void setReadTime(DateMaker readTime) {
        this.readTime = readTime;
    }
}


class SellingBook extends Book {
    private long price;
    private double discount;

    public SellingBook(String id, String title, String categoryId, String libraryId, String writer, String publication,
                       String publishYear, int count, long price, double discount) {
        super(id, title, categoryId, libraryId, writer, publication, publishYear, count);
        this.price = price;
        this.discount = discount;
    }

    public boolean isSellable() {
        if (count==0) return false;
        else return true;
    }

    public void sell() {
        count--;
    }
}


class Thesis extends Resource implements Borrowable {
    private String studentName;
    private String professorName;
    private String defenseYear;


    public Thesis(String id, String title, String categoryId, String libraryId,
                  String studentName, String professorName, String defenseYear, int count) {
        super(id, title, categoryId, libraryId, count);
        this.studentName = studentName;
        this.professorName = professorName;
        this.defenseYear = defenseYear;
    }

    @Override
    public boolean isSearchAcceptable(String word) {
        if (super.isSearchAcceptable(word) || studentName.toLowerCase().contains(word) ||
                professorName.toLowerCase().contains(word)) return true;
        else return false;
    }

    @Override
    public boolean isBorrowable() {
        if (count == 0) return false;
        else return true;
    }

    @Override
    public void borrow() {
        count--;
    }

    @Override
    public void returning() {
        count++;
    }
}


interface Borrowable {
    boolean isBorrowable();

    void borrow();

    void returning();
}


interface Commenter {
    void putComment(Resource resource, String comment);
}


interface Searcher {
    ArrayList<String> searchUser(Collection<User> users,String word);
}
