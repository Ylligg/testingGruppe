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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void test_lagreKunde() {

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
    public void test_HentAlleOK() {

        // lager kunder og liste

        Kunde kunde1 = new Kunde("12345678911", "Ylli", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");
        Kunde kunde2 = new Kunde("67676767676", "Ulaksh", "Gashi", "Olebrummsvei 27", "1054", "OSLO", "40168017","ylli1105");
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



}



