package com.twu;


public class UserOperatingSystem {
    private User user;
    private TrendingTopicRankingSystem rankingSystem;

    public UserOperatingSystem() {
        this.rankingSystem = new TrendingTopicRankingSystem();
    }

    public void login() {
        try {
            displayLogin();
            String input = Utilities.getInput();
            switch (input) {
                case "1":
                    normalUserLogin();
                    login();
                    break;
                case "2":
                    adminLogin();
                    login();
                    break;
                case "3":
                    break;
                default:
                    Utilities.print("你的输入不对！");
                    Utilities.printDividedLine();
                    login();
            }
        }
        catch (Exception e) {
            Utilities.print(e.getMessage());
            Utilities.printDividedLine();
            login();
        }
    }

    private void operatedByNormal() {
        try {
            displayNormalMenu();
            String input = Utilities.getInput();
            while (!input.equals("4")) {
                executeNormalMenu(input);
                Utilities.printDividedLine();
                displayNormalMenu();
                input = Utilities.getInput();
            }
        }
        catch (Exception e) {
            Utilities.print(e.getMessage());
            Utilities.printDividedLine();
            operatedByNormal();
        }
    }

    private void operatedByAdmin() {
        try {
            displayAdminMenu();
            String input = Utilities.getInput();
            while (!input.equals("4")) {
                executeAdminMenu(input);
                Utilities.printDividedLine();
                displayAdminMenu();
                input = Utilities.getInput();
            }
        } catch (Exception e) {
            Utilities.print(e.getMessage());
            Utilities.printDividedLine();
            operatedByAdmin();
        }
    }

    private void normalUserLogin() {
        Utilities.print("请输入用户名： ");
        String name = Utilities.getInput();
        if (name.length() == 0) Utilities.sendError("用户名不能为空哦");
        user = new NormalUser(name);
        operatedByNormal();
    }

    private void adminLogin() {
        Utilities.print("请输入用户名： ");
        String name = Utilities.getInput();
        if (!name.equals(Admin.adminName)) Utilities.sendError("输入的用户名不对！");
        Utilities.print("请输入密码： ");
        String password = Utilities.getInput();
        if (!password.equals(Admin.adminPassword)) Utilities.sendError("输入的密码不对！");
        user = new Admin(name, password);
        operatedByAdmin();
    }

    private void displayLogin() {
        Utilities.print("请选择登录身份：");
        Utilities.print("1. 用户");
        Utilities.print("2. 管理员");
        Utilities.print("3. 退出");
    }

    private void voteTopic() {
        NormalUser u = (NormalUser) user;
        if (u.getNumOfVotes() == 0) Utilities.sendError("你没有票可以投了哦");
        Utilities.print("请输入要投票的热搜：");
        String content = Utilities.getInput();
        TrendingTopic topic = rankingSystem.searchTopic(content);
        int lastVotes = u.getNumOfVotes();
        Utilities.print("投多少票呢？(你还有" + lastVotes + "票。)");
        String input = Utilities.getInput();
        int voteNumb = Utilities.strToNumb(input);
        if (lastVotes < voteNumb) Utilities.sendError("你没有那么多票哦！");
        rankingSystem.voteTopic(topic, voteNumb);
        u.voted(voteNumb);
        Utilities.print("投票成功！");
    }

    private void addTopic() {
        Utilities.print("请输入要添加的热搜：");
        String content = Utilities.getInput();
        rankingSystem.addTopic(content);
        Utilities.print("添加成功！");
    }

    private void addSuperTopic() {
        Utilities.print("请输入要添加的热搜：");
        String content = Utilities.getInput();
        rankingSystem.addSuperTopic(content);
        Utilities.print("添加成功！");
    }

    private void displayTopic() {
        for(TrendingTopic t : rankingSystem.getTopics()) {
            Utilities.print(t.getRanking() + " " + t.getContent() + " " + t.getVotes());
        }
    }

    private void displayNormalMenu() {
        Utilities.print("你好， " + user.getName() + "你想做什么呢？");
        Utilities.print("1. 查看热搜排行榜");
        Utilities.print("2. 为热搜投票");
        Utilities.print("3. 添加热搜");
        Utilities.print("4. 退出");
    }

    private void displayAdminMenu() {
        Utilities.print("你好， " + user.getName() + "你想做什么呢？");
        Utilities.print("1. 查看热搜排行榜");
        Utilities.print("2. 添加热搜");
        Utilities.print("3. 添加超级热搜");
        Utilities.print("4. 退出");
    }

    private void executeNormalMenu(String choice) {
        switch (choice) {
            case "1" :
                displayTopic();
                break;
            case  "2" :
                voteTopic();
                break;
            case "3" :
                addTopic();
                break;
            default: Utilities.print("！！你的输入有误！");
        }
    }

    private void executeAdminMenu(String choice) {
        switch (choice) {
            case "1" :
                displayTopic();
                break;
            case  "2" :
                addTopic();
                break;
            case "3" :
                addSuperTopic();
                break;
            default: Utilities.print("！！你的输入有误！");
        }
    }
}
