package model.buildings;

public class Processor extends Building {
    private int rate;
    private ProcessorType processorType;

    public Processor(int rate, ProcessorType processorType) {
        this.rate = rate;
        this.processorType = processorType;
    }
}
