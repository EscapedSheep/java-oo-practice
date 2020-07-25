package com.twu;


public class UserOperatingSystem {
    private final static UserOperatingSystem userOperationSystem = new UserOperatingSystem();

    private User user;

    final private TrendingTopicRankingSystem rankingSystem = TrendingTopicRankingSystem.getInstance();

    private UserOperatingSystem() {};

    public static UserOperatingSystem getInstance() {
        return userOperationSystem;
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

    public void exit() {
        Utilities.printDividedLine();
        Utilities.print("谢谢使用");
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

    private void operatedByNormal() {
        try {
            displayNormalMenu();
            String input = Utilities.getInput();
            while (!input.equals("5")) {
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

        if (lastVotes < 1) Utilities.sendError("至少要投一票！");
        if (lastVotes < voteNumb) Utilities.sendError("你没有那么多票哦！");

        rankingSystem.voteTopic(topic, voteNumb);
        u.voted(voteNumb);
        Utilities.print("投票成功！");
    }

    private void payTopic() {
        Utilities.print("请输入要购买的热搜：");
        String content = Utilities.getInput();
        TrendingTopic topic = rankingSystem.searchTopic(content);

        Utilities.print("请输入要购买的排名：");
        String input = Utilities.getInput();
        int ranking = Utilities.strToNumb(input);

        if (ranking > rankingSystem.getTopicsNumb()) Utilities.sendError("一共也没有" + ranking +"条热搜！");

        int rankingPaid = rankingSystem.getRankingPaid(ranking);
        if (rankingPaid > 0) Utilities.print("这个位置已经被付了" + rankingPaid + "元。");
        Utilities.print("请输入你要花费的金额： ");
        input = Utilities.getInput();
        int money = Utilities.strToNumb(input);

        if (money < 1) Utilities.sendError("这不是有效金额！");

        rankingSystem.payTopic(topic, ranking, money);
        Utilities.print("购买成功！");
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
        Utilities.print(">>>>>>>>>>实时热搜榜<<<<<<<<<<");
        for(TrendingTopic t : rankingSystem.getTopics()) {
            Utilities.print(t.getRanking() + " " + t.getContent() + " " + t.getVotes());
        }
    }

    private void displayNormalMenu() {
        Utilities.print("你好， " + user.getName() + ", 你想做什么呢？");
        Utilities.print("1. 查看热搜排行榜");
        Utilities.print("2. 购买热搜");
        Utilities.print("3. 为热搜投票");
        Utilities.print("4. 添加热搜");
        Utilities.print("5. 退出");
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
            case "1":
                displayTopic();
                break;
            case  "2":
                payTopic();
                break;
            case "3":
                voteTopic();
                break;
            case "4":
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
