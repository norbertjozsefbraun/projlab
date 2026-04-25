package model.items;
import model.map.Field;

public class Salter extends ResourceConsumingHead {
    private Salt salt = new Salt();

    /** Sets the salt resource for the Salter head.
     * @param s the Salt resource to set
     */
    public void setSalt(Salt s) {
        this.salt = s;
    }

    /** Cleans the field by applying salt to it using the Salter head.
     * Consumes salt if the head has resources, otherwise does nothing.
     * @param f the field to clean
     */
    @Override
    public void clean(Field f) {
        if (this.hasResource()) {
            salt.consume(5);

            f.getSurface().applySalt();
            
            System.out.println("\t[Note: A mező mostantól sózott.]");
        } else {
            System.out.println("\t[Note: Nincs elég só, a sózás elmarad.]");
        }

    }

    /** Refills the Salter head with the specified resource.
     * @param r the resource to refill with
     */
    @Override
    public void refill(Resource r) {
        if (r instanceof Salt) {
            this.addAmount(r.getAmount());
            System.out.println("Sikeres újratöltés nyugtázása");
        } else {
            System.out.println("Nem kompatibilis típus!");
        }
        
    }

    /** Adds the specified amount of salt to the Salter head.
     * @param amount the amount of salt to add
     */
    public void addAmount(int amount) {
        this.salt.add(amount);

    }

    /** Checks if the Salter head has enough salt to perform its cleaning function.
     * @return true if the head has enough salt, false otherwise
     */
    @Override
    public boolean hasResource() {
        return this.salt.getAmount() >= 5;
    }

}