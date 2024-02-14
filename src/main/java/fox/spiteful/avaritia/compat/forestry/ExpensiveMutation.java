package fox.spiteful.avaritia.compat.forestry;

import java.util.ArrayList;
import java.util.Collection;

import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;

public class ExpensiveMutation implements IBeeMutation {

    private IAlleleBeeSpecies mom;
    private IAlleleBeeSpecies dad;
    private IAllele[] template;
    private boolean secret = false;
    private float baseChance;

    public ExpensiveMutation(IAlleleBeeSpecies first, IAlleleBeeSpecies second, IAllele[] result, float chance) {
        mom = first;
        dad = second;
        template = result;
        baseChance = chance;
        BeeManager.beeRoot.registerMutation(this);
    }

    public static void mutate() {
        IAlleleBeeSpecies first;
        IAlleleBeeSpecies second;
        if (Ranger.extra) first = Allele.getExtraSpecies("Relic");
        else first = Allele.getBaseSpecies("Austere");
        second = Allele.getBaseSpecies("Hermitic");
        new ExpensiveMutation(first, second, Genomes.getBalanced(), 0.8f);
        if (Ranger.magic) first = Allele.getMagicSpecies("Draconic");
        else first = Allele.getBaseSpecies("Heroic");
        new ExpensiveMutation(first, GreedyBeeSpecies.ANNOYING, Genomes.getTedious(), 0.7f);
        if (Ranger.extra) first = Allele.getExtraSpecies("Virulent");
        else if (Ranger.magic) first = Allele.getMagicSpecies("Withering");
        else first = Allele.getBaseSpecies("Demonic");
        new ExpensiveMutation(first, GreedyBeeSpecies.TEDIOUS, Genomes.getInsufferable(), 0.6f);

        new ExpensiveMutation(
            Allele.getBaseSpecies("Edenic"),
            GreedyBeeSpecies.INSUFFERABLE,
            Genomes.getInfinite(),
            0.4f);
        if (Ranger.magic) first = Allele.getMagicSpecies("Doctoral");
        else if (Ranger.extra) first = Allele.getExtraSpecies("Diamond");
        else first = Allele.getBaseSpecies("Phantasmal");

        new ExpensiveMutation(first, GreedyBeeSpecies.TRIPPY, Genomes.getCosmic(), 0.8f);

        if (Ranger.magic) first = Allele.getMagicSpecies("Firey");
        else if (Ranger.extra) first = Allele.getExtraSpecies("Volcanic");
        else first = Allele.getBaseSpecies("Industrious");
        // new ExpensiveMutation(first, GreedyBeeSpecies.COSMIC, Genomes.getNeutronium(), 0.6f);
    }

    @Override
    public IBeeRoot getRoot() {
        return BeeManager.beeRoot;
    }

    @Override
    public IAlleleSpecies getAllele0() {
        return mom;
    }

    @Override
    public IAlleleSpecies getAllele1() {
        return dad;
    }

    @Override
    public IAllele[] getTemplate() {
        return template;
    }

    @Override
    public float getBaseChance() {
        return baseChance;
    }

    @Override
    public boolean isSecret() {
        return secret;
    }

    @Override
    public boolean isPartner(IAllele allele) {
        return mom.getUID()
            .equals(allele.getUID())
            || dad.getUID()
                .equals(allele.getUID());
    }

    @Override
    public IAllele getPartner(IAllele allele) {
        if (allele.getUID()
            .equals(mom.getUID())) return dad;
        return mom;
    }

    @Override
    public Collection<String> getSpecialConditions() {
        return new ArrayList<String>();
    }

    @Override
    public float getChance(IBeeHousing housing, IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1,
        IBeeGenome genome0, IBeeGenome genome1) {
        float finalChance = 0f;
        float chance = this.baseChance * 1f;

        if (isPartner(allele0) && isPartner(allele1)) {
            float housingModifier = 1.0f;
            for (IBeeModifier modifier : housing.getBeeModifiers()) {
                housingModifier *= modifier.getMutationModifier(genome0, genome1, chance);
            }
            finalChance = Math.round(
                chance * housingModifier
                    * BeeManager.beeRoot.getBeekeepingMode(housing.getWorld())
                        .getBeeModifier()
                        .getMutationModifier(genome0, genome1, chance));
        }

        return finalChance;
    }

}
