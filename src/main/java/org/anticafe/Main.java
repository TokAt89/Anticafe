package org.anticafe;

import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Класс Main представляет собой программу для управления антикафе, использующую классы VisitService и TableService.
 * Позволяет выполнять различные действия, такие как занятие и освобождение столиков, просмотр информации о столиках, визитах и прибыли, просмотр статистических данных
 */
public class Main {
    /**
     * Представляет собой меню для пользователя антикафе
     */
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static String menu = """

            1. Занять столик
            2. Освободить столик
            3. Вывести занятые столики
            4. Вывести свободные столики
            5. Отобразить, сколько минут гости сидят за каждым столиком
            6. Отобразить, сколько выбранному столику придётся заплатить
            7. Отобразить, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе
            8. Общий заработок
            9. Посмотреть среднюю длительность использования столиков
            10. Узнать, какой столик чаще всего выбирается
            11. Узнать, какой столик больше всего принес в кассу
            12. Получить список всех посещений
            13. Получить список всех завершенных посещений
            14. Выход из программы
            """;
    private static Scanner in = new Scanner(System.in);

    /**
     * Основной блок программы, управляющий выполнением действий пользователя в антикафе
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        logger.info("Запуск программы");

        while (true) {
            System.out.println("\nДобро пожаловать!");
            System.out.println(menu);
            System.out.println("Выберите действие: ");
            String optionLine = in.nextLine();
            String tableIdLine;
            int option = 0;
            int tableId;
            option = toDigit(optionLine);
            switch (option) {
                case 1 -> {
                    System.out.println("Список свободных столиков: ");
                    for (Table table : VisitService.getFreeTables()) {
                        System.out.println(table);
                    }
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    try {
                        Visit visit = VisitService.createVisit(new Client(), tableId);
                        logger.info("Столик успешно занят");
                        System.out.printf("Столик успешно занят.%n ID визита: %d%nСтарт визита: %s", visit.getId(), visit.getFormattedTime());
                    } catch (RuntimeException ex) {
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Занятые столики");
                    System.out.println(VisitService.getReservedTables());
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);

                    try {
                        VisitService.finishVisit(tableId);
                    }

                    catch (RuntimeException ex){
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }
                    logger.info("Столик освобожден");


                }
                case 3 -> {
                    System.out.println("Список занятых столиков: ");
                    for (Table table :VisitService.getReservedTables()){
                        System.out.println(table);
                        logger.info("Выведен список занятых столиков");
                    }

                }
                case 4 -> {
                    System.out.println("Список свободных столиков");
                    for (Table table :VisitService.getFreeTables()){
                        System.out.println(table);
                        logger.info("Выведен список свободных столиков");
                    }
                }
                case 5 ->{
                    System.out.println("Просмотр информации о том, сколько всего минут сидят за каждым столиком");
                    System.out.println(VisitService.getTotalCurrentDuration());
                    logger.info("Осуществлен просмотр информации о том, сколько всего минут сидят за каждым столиком");

                }
                case 6 -> {
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    logger.info("Осуществлен просмотр информации о том, сколько гостям нужно заплатить (конкретным)");

                    try {
                        System.out.println(VisitService.getCurrentCost(tableId));
                    } catch (RuntimeException ex) {
                        logger.error(ex.getMessage());
                        System.out.println(ex.getMessage());
                    }

                }
                case 7 -> {
                    System.out.println("Просмотр информации о том, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе");
                    System.out.println(VisitService.getTotalCurrentDuration());
                    System.out.println(VisitService.getTotalCurrentCost());
                    logger.info("Осуществлен просмотр информации о том, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе");
                }
                case 8 ->{
                    System.out.println("Общий заработок");
                    System.out.println(VisitService.getTotalCostOfAllTime());
                    logger.info("Просмотрен общий заработок");
                }
                case 9 -> {
                    System.out.println("Cредняя занятость столика по времени");
                    Map<Table, DoubleSummaryStatistics> map = VisitService.getAverageDurationOfAllTables();

                    for (Table table:map.keySet()
                    ) {
                        System.out.println(table + ": " + map.get(table).getAverage());
                    }
                    logger.info("Просмотрена информация о том, сколько в среднем занят столик по времени");
                }
                case 10 -> {
                    System.out.println("Столик, который чаще всего выбирается");
                    logger.info("Просмотрена информация о том, какой столик чаще всего выбирается");
                    try {
                        System.out.println(VisitService.getTheMostPopularTable());
                    }
                    catch (RuntimeException ex){
                        System.out.println(ex.getMessage());
                        logger.error(ex.getMessage());
                    }

                }
                case 11 -> {
                    System.out.println("Столик,принесший большего всего прибыли");
                    logger.info("Просмотрена информация о том, какой столик принес больше всего прибыли");
                    try {
                        System.out.println(VisitService.getTheMostEarnedTable());
                    }
                    catch (RuntimeException ex){
                        System.out.println(ex.getMessage());
                        logger.error(ex.getMessage());
                    }

                }
                case 12 -> {
                    System.out.println("Cписок всех визитов:");
                    for (Visit visit:VisitService.getVisits()) {
                        System.out.printf("    Столик: %s%n    Длительность: %d минут%n   Завершен: %s%n", visit.getTable(), visit.getDuration(), visit.isFinished()?"Да":"Нет");
                        if (visit.isFinished())
                            System.out.printf("    Стоимость: %f рублей%n", visit.getCost());
                        System.out.println();
                    }
                    logger.info("Выведен список всех визитов");
                }
                case 13 -> {
                    System.out.println("Список всех завершенных визитов");
                    for (Visit visit:VisitService.getFinishedVisits()) {
                        System.out.printf("    Столик: %s%n    Длительность: %d минут", visit.getTable(), visit.getDuration());
                        if (visit.isFinished())
                            System.out.printf("    Стоимость: %f рублей", visit.getCost());
                    }
                    logger.info("Выведен список всех завершенных визитов");
                }
                case 14 -> {
                    logger.info("Осуществлён выход из программы");
                    System.exit(0);
                }
                default -> System.out.println("Некорректный символ");

            }

        }

    }
    public static int toDigit(String line) {
        return StringUtils.isNumeric(line)? Integer.parseInt(line):0;
    }
}