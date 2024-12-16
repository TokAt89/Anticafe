package org.anticafe;
/**
 * Класс Table представляет собой информацию о столиках: свободен он или занят.
 */
public class Table {
    /**
     * Уникальный идентификатор стола
     */
    private final int id;
    /**
     * Метод показывает, свободен ли столик
     */
    private boolean isFree;

    /**
     * Возвращает уникальный идентификатор стола
     * @return id (уникальный идентификатор стола)
     */

    public int getId() {
        return id;
    }

    /**
     * Метод проверяет, свободен ли столик
     * @return true - столик свободный, false - столик занят
     */

    public boolean isFree() {
        return isFree;
    }

    /**
     * Устанавливает состояние занятости для стола
     * @param free
     * Если true - стол считается свободным, false - стол считается занятым
     */
    public void setFree(boolean free) {
        isFree = free;
    }


    /**
     * Конструктор для создания объекта стола с указанным идентификатором.
     * Стол считается свободным при создании по умолчанию
     * @param id
     */
    public Table(int id) {
        this.id = id;
        this.isFree = true;
    }

    /**
     * Представляет строковое представление объекта стола
     * @return строковое представление стола, например, Столик №5
     */
    public String toString(){
        return String.format("Столик № %d", id);
    }
}
