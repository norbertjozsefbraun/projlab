package model.items;
import model.map.Field;
import test.Prototype;


public class GravelSpreader extends ResourceConsumingHead {
    private Gravel gravel = new Gravel();

    /**
     * Set the gravel resource for the GravelSpreader head.
     * @param gravel
     */
    public void setGravel(Gravel gravel) {
        this.gravel = gravel;
    }

    /**
     * Cleans the field by spreading gravel on it using the GravelSpreader head.
     * Consumes gravel if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        if (this.hasResource()) {
            gravel.consume(5); 
            f.getSurface().addGravel();
        } else {
            System.out.println("\tGravelSpreader üres, a szórás elmarad.");
        }
    }
    /**
     * Refills the GravelSpreader head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        if (r instanceof Gravel) {
            this.addAmount(r.getAmount());
            System.out.println("Sikeres újratöltés nyugtázása"); // [cite: 138]
        } else {
            System.out.println("\tHibás erőforrás típus, a GravelSpreader csak Gravel-t fogad el.");
        }
    }

    /**
     * Adds the specified amount of gravel to the GravelSpreader head.
     * @param amount the amount of gravel to add
     */
    public void addAmount(int amount) {
        int oldAmount = this.gravel.amount;
        this.gravel.add(amount);
        Prototype.getInstance().changed("GravelSpreader", "gravel", String.valueOf(oldAmount), String.valueOf(this.gravel.amount));
    }

    /**
     * Checks if the GravelSpreader head has enough gravel to perform its cleaning function.
     * @return true if the head has enough gravel, false otherwise
     */
    @Override
    public boolean hasResource() {
        return this.gravel.getAmount() >= 5;
    }

    /**
     * Gets the current gravel resource used by this GravelSpreader head.
     * @return the gravel resource
     */
    @Override
    public Resource getResource() {
        return this.gravel;
    }
}