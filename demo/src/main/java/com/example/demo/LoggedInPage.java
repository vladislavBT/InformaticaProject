package com.example.demo;

import com.example.demo.entities.Drinks.Alcoholic.*;
import com.example.demo.entities.Drinks.Non_Alcoholic.*;
import com.example.demo.entities.Food.Fruit.*;
import com.example.demo.entities.Food.Vegetables.*;
import com.example.demo.entities.UserAccount;
import com.example.demo.entities.UserProperties;
import com.example.demo.entities.Week;
import com.example.demo.entities.enums.Gender;
import jakarta.persistence.criteria.CriteriaBuilder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.*;

public class LoggedInPage extends JFrame{
    private JPanel panel;
    private JTextField availableDailyCaloriesField;
    private JTextField totalDailyCaloriesField;
    private JComboBox foodBox;
    private JButton consumedFoodCalculateButton;
    private JButton calculateConsumedCaloriesButton;
    private JTextField eatenFoodField;
    private JTextField consumedCaloriesField;
    private JComboBox drinkBox;
    private JTextField drinkFoodField;
    private JButton consumedDrinkCalculateButton;
    private JTextField totalDailyConsumedCaloriesField;

    private JButton endTheDayButton;
    private JTable table;
    private JScrollPane myScrollPane;
    private JLabel todayDayLabel;
    private JPanel panelForTable;
    private Week week;

    private int counterForTheWeekDays=0;

    private int id;
    private double dailyCaloriesForUser=0;
    public LoggedInPage(UserAccount ourUserAccount){
        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(1000,500);

        id = ourUserAccount.getId();

        totalDailyCaloriesField.setEditable(false);
        availableDailyCaloriesField.setEditable(false);
        totalDailyConsumedCaloriesField.setEditable(false);
        totalDailyConsumedCaloriesField.setText(String.valueOf(0));


        String COLUMNS[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday","SUM"};
        DefaultTableModel model = new DefaultTableModel(COLUMNS,0);

        table.setModel(model);
        table.setAutoCreateRowSorter(true);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        File file = new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserProperties ");
        try {
            Scanner scannerProperties = new Scanner(file);
            id = ourUserAccount.getId();
            int age=0;
            int height=0;
            int weight = 0;
            Gender gender = null;
            UserProperties ourUserProperties = null;
            while (scannerProperties.hasNextLine()){
                String[] line = scannerProperties.nextLine().split(" ");
                if(Integer.parseInt(line[0])==id){
                    age = Integer.parseInt(line[1]);
                    height=Integer.parseInt(line[2]);
                    weight = Integer.parseInt(line[3]);
                    gender = Gender.valueOf(line[4].toUpperCase(Locale.ROOT));
                    break;
                }
            }
            if(gender.toString().equals("MAN")){
                dailyCaloriesForUser = 66+(13.7*weight) + (5*height) - (6.8*age);
            }else if(gender.toString().equals("WOMAN")){
                dailyCaloriesForUser = 655+(9.6*weight) + (1.85*height) - (4.7*age);
            }

            totalDailyCaloriesField.setText(String.format("%.2f",dailyCaloriesForUser));
            availableDailyCaloriesField.setText(String.format("%.2f",dailyCaloriesForUser));
            totalDailyConsumedCaloriesField.setText(String.format("0"));
            todayDayLabel.setText("Monday");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File file1;
        try {
            file1= new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserDailyCalorieProperties");
            Scanner scannerCalorieProperties = new Scanner(file1);

            while(scannerCalorieProperties.hasNextLine()){
                String line = scannerCalorieProperties.nextLine();
                String[] calorieProperties = line.split(" ");
                int idOfTheLine = Integer.parseInt(calorieProperties[0]);
                double totalDailyCalories = Double.parseDouble(calorieProperties[1].replace(',','.'));
                double availableDailyCalories = Double.parseDouble(calorieProperties[2].replace(',','.'));
                double totalDailyConsumedCalories = Double.parseDouble(calorieProperties[3].replace(',','.'));
                String todayDay = calorieProperties[4];

                if(idOfTheLine==id){
                    totalDailyCaloriesField.setText(String.valueOf(totalDailyCalories));
                    availableDailyCaloriesField.setText(String.valueOf(availableDailyCalories));
                    totalDailyConsumedCaloriesField.setText(String.valueOf(totalDailyConsumedCalories));
                    todayDayLabel.setText(todayDay);
                }
            }
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        File file2;
        try {
            file2= new File("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserHistory");
            Scanner scannerCalorieHistory = new Scanner(file2);
            while (scannerCalorieHistory.hasNextLine()) {
                String[] properties = scannerCalorieHistory.nextLine().split(" ");
                if (id == Integer.parseInt(properties[0])) {

                    if(properties[1].equals("1")){
                        week = new Week();
                        week.setMonday(Double.parseDouble(properties[2].replace(',','.')));
                    }else if(properties[1].equals("2")){
                        week.setTuesday(Double.parseDouble(properties[2].replace(',','.')));
                    }else if(properties[1].equals("3")){
                        week.setWednesday(Double.parseDouble(properties[2].replace(',','.')));
                    }else if(properties[1].equals("4")){
                        week.setThursday(Double.parseDouble(properties[2].replace(',','.')));
                    }else if(properties[1].equals("5")){
                        week.setSaturday(Double.parseDouble(properties[2].replace(',','.')));
                    }else if(properties[1].equals("6")){
                        week.setSunday(Double.parseDouble(properties[2].replace(',','.')));
                        String[] propertiesForSum = scannerCalorieHistory.nextLine().split(" ");
                        String[] weekValues = new String[8];
                        weekValues[0]=String.valueOf(week.getMonday());
                        weekValues[1]=String.valueOf(week.getTuesday());
                        weekValues[2]=String.valueOf(week.getWednesday());
                        weekValues[3]=String.valueOf(week.getThursday());
                        weekValues[4]=String.valueOf(week.getFriday());
                        weekValues[5]=String.valueOf(week.getSaturday());
                        weekValues[6]=String.valueOf(week.getSaturday());
                        weekValues[7] = String.valueOf(String.format("%.2f",Double.parseDouble(propertiesForSum[2].replace(',','.'))));
                        model.addRow(weekValues);
                    }
                    counterForTheWeekDays++;
                    if(counterForTheWeekDays==7){
                        counterForTheWeekDays=0;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                List<String[]> allLines = new ArrayList<>();
                try {
                    Scanner scannerCalorieProperties = new Scanner(file1);

                    while (scannerCalorieProperties.hasNextLine()){
                        String line = scannerCalorieProperties.nextLine();
                        String[] calorieProperties = line.split(" ");
                        allLines.add(calorieProperties);
                    }

                    int existenceOfTheUser = 0;
                    String lineUser;
                    for (int i = 0; i < allLines.size(); i++) {
                        String totalDailyCalories = totalDailyCaloriesField.getText();
                        String availableDailyCalories = availableDailyCaloriesField.getText();
                        String totalDailyConsumedCalories = totalDailyConsumedCaloriesField.getText();
                        String todayDay = todayDayLabel.getText();
                        lineUser=String.format("%d %s %s %s %s",id,totalDailyCalories,availableDailyCalories,totalDailyConsumedCalories,todayDay);
                        String []lineSplit = lineUser.split(" ");
                        if(Integer.parseInt(allLines.get(i)[0])==id){
                            allLines.set(i,lineSplit);
                            existenceOfTheUser=1;
                        }
                    }
                    if(existenceOfTheUser==0){
                        String totalDailyCalories = totalDailyCaloriesField.getText();
                        String availableDailyCalories = availableDailyCaloriesField.getText();
                        String totalDailyConsumedCalories = totalDailyConsumedCaloriesField.getText();
                        String todayDay = todayDayLabel.getText();
                        lineUser=String.format("%d %s %s %s %s",id,totalDailyCalories,availableDailyCalories,totalDailyConsumedCalories,todayDay);
                        String []lineSplit = lineUser.split(" ");
                        allLines.add(lineSplit);
                    }

                    BufferedWriter bufferedWriterToDelete = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserDailyCalorieProperties"));
                    bufferedWriterToDelete.write("");
                    bufferedWriterToDelete.close();

                    BufferedWriter bufferedWriterToWrite = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserDailyCalorieProperties"));
                    for (String[] line : allLines) {
                        String lineToString =String.format("%d %s %s %s %s%n", Integer.parseInt(line[0]),line[1],line[2],line[3],line[4]);
                        bufferedWriterToWrite.write(lineToString);
                    }

                    bufferedWriterToWrite.close();
                    e.getWindow().dispose();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

//////////////////////////////




        calculateConsumedCaloriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double consumedCalories = Double.parseDouble(consumedCaloriesField.getText().replace(',','.'));
                    if(consumedCalories<0){
                        JOptionPane.showMessageDialog(null, "Consumed calories can't be under zero", "Error", JOptionPane.ERROR_MESSAGE);
                    }else {
                        double availableCalories = Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                        availableDailyCaloriesField.setText(String.format("%.2f",availableCalories - consumedCalories));

                        double totalDailyConsumedCalories = Double.parseDouble(totalDailyConsumedCaloriesField.getText().replace(',','.'));
                        totalDailyConsumedCaloriesField.setText(String.format("%.2f",totalDailyConsumedCalories+consumedCalories));

                        if(Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'))<=0){
                            JOptionPane.showMessageDialog(null, "You've reached your daily calories limit!", "Daily calories limit", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "It's not a number", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        consumedFoodCalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String typeOfFood = foodBox.getSelectedItem().toString();
                    double eatenAmountOfFood = Double.parseDouble(eatenFoodField.getText().replace(',','.'));
                    double eatenAmountOfFoodCoefficient = eatenAmountOfFood/100;
                    if(typeOfFood.equals("")){
                        JOptionPane.showMessageDialog(null, "You didn't choose food", "Error", JOptionPane.ERROR);
                    }else if(eatenAmountOfFood<0){
                        JOptionPane.showMessageDialog(null, "You can't eat negative amount of food", "Error", JOptionPane.ERROR);
                    }
                    double calories = 0.0;
                    switch (typeOfFood){
                        case "Apple":
                            calories = Apple.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Banana":
                            calories = Banana.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Blueberries":
                            calories = Blueberries.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Grapefruit":
                            calories = Grapefruit.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Grapes":
                            calories = Grapes.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Lemon":
                            calories = Lemon.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Oranges":
                            calories = Oranges.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Peaches":
                            calories = Peaches.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Pear":
                            calories = Pear.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Pineapple":
                            calories = Pineapple.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Plums":
                            calories = Plums.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Raspberries":
                            calories = Raspberries.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Strawberries":
                            calories = Strawberries.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Watermelon":
                            calories = Watermelon.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Beans":
                            calories = Beans.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Broccoli":
                            calories = Broccoli.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Carrot":
                            calories = Carrot.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "CherryTomato":
                            calories = CherryTomato.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Cabbage":
                            calories = Cabbage.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Corn":
                            calories = Corn.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Cucumber":
                            calories = Cucumber.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Garlic":
                            calories = Garlic.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Mushrooms":
                            calories = Mushrooms.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Olives":
                            calories = Olives.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Peas":
                            calories = Peas.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Pepper":
                            calories = Pepper.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Potato":
                            calories = Potato.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Pumpkin":
                            calories = Pumpkin.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Spinach":
                            calories = Spinach.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                        case "Tomato":
                            calories = Tomato.CALORIES*eatenAmountOfFoodCoefficient;
                            break;
                    }


                    totalDailyConsumedCaloriesField.setText(String.format("%.2f",Double.parseDouble(totalDailyConsumedCaloriesField.getText().replace(',','.'))+calories));
                    double currentCalories = Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    availableDailyCaloriesField.setText(String.format("%.2f",currentCalories-calories));
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Incorrect amount of food", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        consumedDrinkCalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String typeOfDrink = drinkBox.getSelectedItem().toString();
                    double consumedCaloriesFromDrink = Double.parseDouble(drinkFoodField.getText().replace(',','.'));
                    double consumedAmountOfDrinkCoefficient = consumedCaloriesFromDrink/100;
                    if(typeOfDrink.equals("")){
                        JOptionPane.showMessageDialog(null, "You didn't choose food", "Error", JOptionPane.ERROR);
                    }else if(consumedCaloriesFromDrink<0){
                        JOptionPane.showMessageDialog(null, "You can't eat negative amount of food", "Error", JOptionPane.ERROR);
                    }
                    double calories = 0.0;
                    switch (typeOfDrink) {
                        case "Beer":
                            calories = Beer.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Gin":
                            calories = Gin.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Merlot":
                            calories = Merlot.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Rum":
                            calories = Rum.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Vodka":
                            calories = Vodka.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Whiskey":
                            calories = Whiskey.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "WhiteWine":
                            calories = WhiteWine.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Coca_Cola":
                            calories = Coca_Cola.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "IceTea":
                            calories = IceTea.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Milk":
                            calories = Milk.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Nestea":
                            calories = Nestea.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Non_AlcoholicBeer":
                            calories = Non_AlcoholicBeer.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Strawberries":
                            calories = Strawberries.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Sprite":
                            calories = Sprite.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                        case "Tea":
                            calories = Tea.CALORIES * consumedAmountOfDrinkCoefficient;
                            break;
                    }


                    totalDailyConsumedCaloriesField.setText(String.format("%.2f",Double.parseDouble(totalDailyConsumedCaloriesField.getText().replace(',','.'))+calories));
                    double currentCalories = Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    availableDailyCaloriesField.setText(String.format("%.2f",currentCalories-calories));
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Incorrect amount of food", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        endTheDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
///////////////////////////////////////////////////////// scanning the file so it can be saved
                List<String> historyList = new ArrayList<>();
                try {
                    Scanner scannerCalorieHistory = new Scanner(file2);
                    while (scannerCalorieHistory.hasNextLine()){
                        historyList.add(scannerCalorieHistory.nextLine());
                    }

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
///////////////////////////////////////////////////////// saving the day calories into the history
                BufferedWriter bufferedWriterToWrite = null;
                try {
                    bufferedWriterToWrite = new BufferedWriter(new FileWriter("D:\\Intelij\\IdeaProjects\\InformaticaProject\\demo\\src\\main\\resources\\UserHistory"));

                    int dayCode = 0;
                    double referenceToDayCode=0;
                    String save1="";
                    switch (counterForTheWeekDays){
                        case 0:
                            dayCode= 1;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 1:
                            dayCode= 2;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 2:
                            dayCode= 3;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 3:
                            dayCode= 4;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 4:
                            dayCode= 5;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 5:
                            dayCode= 6;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            break;
                        case 6:
                            dayCode= 7;
                            referenceToDayCode = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                            save1 = String.format("%d %d %.2f",id,dayCode,referenceToDayCode);
                            historyList.add(save1);
                            String save2 = String.format("%d %d %.2f",id,dayCode+1,referenceToDayCode);
                            historyList.add(save2);
                            break;
                    }
                    for (int i = 0; i < historyList.size(); i++) {
                        bufferedWriterToWrite.write(String.format("%s%n",historyList.get(i)));
                    }

                    bufferedWriterToWrite.close();


                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                if(counterForTheWeekDays==0){
                    week = new Week();
                    double mondayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setMonday(mondayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Tuesday");
                }else if(counterForTheWeekDays==1){
                    double tuesdayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setTuesday(tuesdayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Wednesday");
                }else if(counterForTheWeekDays==2){
                    double wednesdayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setWednesday(wednesdayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Thursday");
                }else if(counterForTheWeekDays==3){
                    double thursdayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setThursday(thursdayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Friday");
                }else if(counterForTheWeekDays==4){
                    double fridayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setFriday(fridayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Saturday");
                }else if(counterForTheWeekDays==5){
                    double saturdayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setSaturday(saturdayCalories);
                    counterForTheWeekDays++;
                    todayDayLabel.setText("Sunday");

                } else if(counterForTheWeekDays==6){
                    double sundayCalories = -Double.parseDouble(availableDailyCaloriesField.getText().replace(',','.'));
                    week.setSunday(sundayCalories);
                    todayDayLabel.setText("Monday");
                    double sum = week.getMonday()+week.getTuesday()+week.getThursday()+week.getFriday()+week.getSaturday()+week.getSunday();

                    counterForTheWeekDays=0;
                    String[] weekValues = new String[8];
                    weekValues[0]=String.valueOf(week.getMonday());
                    weekValues[1]=String.valueOf(week.getTuesday());
                    weekValues[2]=String.valueOf(week.getWednesday());
                    weekValues[3]=String.valueOf(week.getThursday());
                    weekValues[4]=String.valueOf(week.getFriday());
                    weekValues[5]=String.valueOf(week.getSaturday());
                    weekValues[6]=String.valueOf(week.getSaturday());
                    weekValues[7] = String.valueOf(String.format("%.2f",sum));
                    model.addRow(weekValues);

                }
                availableDailyCaloriesField.setText(String.format("%.2f",dailyCaloriesForUser));
                totalDailyConsumedCaloriesField.setText(String.format("0"));

            }
        });
    }
    private static class CaloriesTableModel extends DefaultTableModel{

        private final String COLUMNS[] = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        List<Week> weeks;

        public CaloriesTableModel(List<Week> weeks) {
            this.weeks=weeks;
        }

        public void addWeek(Week week){
            weeks.add(week);
        }

        @Override
        public int getRowCount() {
            return weeks.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex){
                case 0 -> weeks.get(rowIndex).getMonday();
                case 1 -> weeks.get(rowIndex).getTuesday();
                case 2 -> weeks.get(rowIndex).getWednesday();
                case 3 -> weeks.get(rowIndex).getThursday();
                case 4 -> weeks.get(rowIndex).getFriday();
                case 5 -> weeks.get(rowIndex).getSaturday();
                case 6 -> weeks.get(rowIndex).getSunday();
                default -> throw new IllegalStateException("Unexpected value: " + columnIndex);
            };
        }

        public CaloriesTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public String getColumnName(int column) {
            return super.getColumnName(column);
        }

        @Override
        public void addColumn(Object columnName) {
            super.addColumn(columnName);
        }
    }

}
