package org.anticafe;

/**
 * Класс Client представляет собой объект клиента с уникальным идентификатором
 */
public class Client {
    /**
     *Последний присвоенный идентификатор клиента
     */
    private static int lastId = 0;
    /**
     * Уникальный идентификатор клиента
     */
    private int id;

    /**
     * Возвращает уникальный идентификатор клиента
     * @return id (уникальный идентификатор клиента)
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор клиента
     * @param id (новый уникальный идентификатор клиента)
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Присваивает уникальный идентификационный номер клиенту. Создается по умолчанию
     */
    public Client() {
        this.id = lastId++;
    }
}
