package model.items;
import model.map.Field;

public class Dragon extends ResourceConsumingHead {
    private Biokerosene kerosene = new Biokerosene();
    private boolean isIgnited = false;

    /**
     * Set the kerosene resource for the Dragon head.
     * @param kerosene
     */
    public void setKerosene(Biokerosene kerosene) {
        this.kerosene = kerosene;
    }

    /**
     * Cleans the field by melting all the snow on it using the Dragon head.
     * Consumes kerosene if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {

        if (this.hasResource()) {
            kerosene.consume(10); 
            isIgnited = true;
            f.getSurface().meltAll();
        } else {
            isIgnited = false;
            System.out.println("\tDragon üres, a takarítás elmarad.");
        }
        isIgnited = false;

    }
    /**
     * Refills the Dragon head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        if (r instanceof Biokerosene) {
            this.addAmount(r.getAmount());
            System.out.println("Sikeres újratöltés nyugtázása"); //
        } else {
            System.out.println("\tHibás erőforrás típus, a Dragon csak Biokerosene-t fogad el.");
        }
    }
    /**
     * Adds the specified amount of kerosene to the Dragon head.
     * @param amount the amount of kerosene to add
     */
    public void addAmount(int amount) {
        if (this.kerosene.amount + amount > this.kerosene.maxAmount) {
            this.kerosene.amount = this.kerosene.maxAmount;
        } else {
            this.kerosene.add(amount);
        }
    }
    
    /**
     * Checks if the Dragon head has enough kerosene to perform its cleaning function.
     * @return true if the head has enough kerosene, false otherwise
     */
    @Override
    public boolean hasResource() {
        return this.kerosene.getAmount() >= 10;
    }
}