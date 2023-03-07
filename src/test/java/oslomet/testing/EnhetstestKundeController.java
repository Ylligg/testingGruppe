package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKundeController {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository adminKundeRepository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;


    @Test
    public void test_lagreKundeOK() {

        Kunde kunde1 = new Kunde("12345654321", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");

        when(sjekk.loggetInn()).thenReturn("12345654321");

        Mockito.when(adminKundeRepository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_lagreKundeFeil() {
        Kunde kunde1 = new Kunde("12345654321", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");

        when(sjekk.loggetInn()).thenReturn("12345654321");

        Mockito.when(adminKundeRepository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        // act
        String resultat = adminKundeController.lagreKunde(kunde1);

        // assert
        assertEquals("Feil",resultat);
    }

    @Test
    public void test_lagreKundeikkeloggetinn() {
        Kunde kunde1 = new Kunde("12345654321", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");

        String resultat = adminKundeController.lagreKunde(kunde1);

        assertEquals("Ikke logget inn",resultat);
    }

    @Test
    public void test_HentAlleOK() {

        // lager kunder og liste

        Kunde kunde1 = new Kunde("12345678911", "Ylli", "Gashi", "Hogwarts 27", "1069", "OSLO", "08162552","123");
        Kunde kunde2 = new Kunde("67676767676", "Ulaksh", "Gashi", "Wakanda 4 EVA", "2030", "OSLO", "8901272","213");
        List<Kunde> kundeliste = new ArrayList<>();
        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        // ser om at de er logget inn

        when(sjekk.loggetInn()).thenReturn("12345678911");

        // kaller på medtoden og lister ut kundene

        when(adminKundeRepository.hentAlleKunder()).thenReturn(kundeliste);

        List<Kunde> resultat = adminKundeController.hentAlle();

        assertEquals(kundeliste, resultat);

    }

    @Test
    public void test_hentAlleFeil() {

        // act
        List<Kunde> resultat = adminKundeController.hentAlle();

        // assert
        assertNull(resultat);
    }

    @Test
    public void test_endreKundeInfoOK() {

        when(sjekk.loggetInn()).thenReturn("45626252411");

        Kunde kunde1 = new Kunde("45626252411", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","passport");

        Mockito.when(adminKundeRepository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String restulat = adminKundeController.endre(kunde1);
        Assert.assertEquals("OK", restulat);
    }

    @Test
    public void test_endreKundeInfoFeil() {

        when(sjekk.loggetInn()).thenReturn("9898765212");

        Kunde kunde1 = new Kunde("9898765212", "Jelleh", "Gashi", "More-ndin 12", "2020", "Asker", "2010284663","passord");


        Mockito.when(adminKundeRepository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        String restulat = adminKundeController.endre(kunde1);
        Assert.assertEquals("Feil", restulat);
    }

    @Test
    public void test_endreKundeikkeloggetinn() {
        Kunde kunde1 = new Kunde("12345654321", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");

        String resultat = adminKundeController.endre(kunde1);

        assertEquals("Ikke logget inn",resultat);
    }

    @Test
    public void test_slettKundeOK() {

        when(sjekk.loggetInn()).thenReturn("12345678911");

        Mockito.when(adminKundeRepository.slettKunde(anyString())).thenReturn("OK");

        String resultat = adminKundeController.slett("12345678911");
        Assert.assertEquals("OK", resultat);
    }

    @Test
    public void test_slettEnKundeFeil() {

        when(sjekk.loggetInn()).thenReturn("12345678911");

        Mockito.when(adminKundeRepository.slettKunde(anyString())).thenReturn("Feil");

        String resultat = adminKundeController.slett("12345678911");
        Assert.assertEquals("Feil", resultat);
    }

    @Test
    public void test_slettKundeikkeloggetinn() {
        Kunde kunde1 = new Kunde("12345654321", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");

        String resultat = adminKundeController.slett(kunde1.getPersonnummer());

        assertEquals("Ikke logget inn",resultat);
    }









}



