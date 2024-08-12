package zcla71.repository;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public abstract class SingleFileRepository<T> extends Repository<T> {
    private String fileLocation;
    private Boolean inTransaction;

    public SingleFileRepository(Class<T> classe, String fileLocation, Boolean readFile) throws Exception {
        super(classe);
        this.fileLocation = fileLocation;
        this.inTransaction = false;
        try {
            this.setData(this.getClasse().getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }
        if (readFile) {
            this.readData();
        }
    }

    @Override
    public void beginTransaction() {
        if (this.inTransaction) {
            throw new RuntimeException("Já há transação iniciada");
        }
        this.inTransaction = true;
    }

    @Override
    public void commitTransaction() throws Exception {
        if (!this.inTransaction) {
            throw new RuntimeException("Não há transação iniciada");
        }
        saveData();
        this.inTransaction = false;
    }

    @Override
    public void rollbackTransaction() throws Exception {
        if (!this.inTransaction) {
            throw new RuntimeException("Não há transação iniciada");
        }
        readData();
        this.inTransaction = false;
    }

    protected File getFile() {
        return new File(this.fileLocation);
    }

    public abstract T readData() throws Exception;
    public abstract void saveData() throws Exception;
}
