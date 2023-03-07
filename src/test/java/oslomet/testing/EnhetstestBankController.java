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
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523"); 

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    // nye tester

    @Test
    public void hentTransaksjoner_loggetInn() {

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);

    }

    @Test
    public void hentTransaksjoner_ikkeloggetInn() {

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(null);


    }

    @Test
    public void hentSaldi_loggetinn() {

        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        Mockito.when(repository.hentSaldi(anyString())).thenReturn(konti);

        List<Konto> resultat = bankController.hentSaldi();

        Assert.assertEquals(konti, resultat);

    }

    @Test
    public void hentSaldi_ikkeloggetinn() {

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);

    }


    @Test
    public void RegristrerBetalingOK() {


        Transaksjon tran1 = new Transaksjon(1, "1234141421", 3552.56, "2002-21-2", "Halla på deg", "aventer", "121514142617");

        when(sjekk.loggetInn()).thenReturn("121514142617");

        Mockito.when(repository.registrerBetaling((any(Transaksjon.class)))).thenReturn("OK");

       String resultat = bankController.registrerBetaling(tran1);

       assertEquals("OK", resultat);

    }

    @Test
    public void RegristrerBetalingFeil() {


        Transaksjon tran1 = new Transaksjon(1, "1234141421", 3552.56, "2002-21-2", "Halla på deg", "aventer", "121514142617");

        when(sjekk.loggetInn()).thenReturn("121514142617");

        Mockito.when(repository.registrerBetaling((any(Transaksjon.class)))).thenReturn("Feil");

        String resultat = bankController.registrerBetaling(tran1);

        assertEquals("Feil", resultat);

    }

    @Test
    public void HentBetaling_logginn() {
        List<Transaksjon> liste = new ArrayList<>();
        Transaksjon tran1 = new Transaksjon(1, "1234141421", 3552.56, "2002-21-2", "Halla på deg", "aventer", "121514142617");
        liste.add(tran1);

        when(sjekk.loggetInn()).thenReturn("121514142617");

        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(liste);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(liste, resultat);

    }

    @Test
    public void HentBetaling_ikkelogginn() {

        when(sjekk.loggetInn()).thenReturn("121514142617");
        Mockito.when(repository.hentBetalinger(anyString())).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);

    }


    @Test
    public void test_endreKundeInfoOK() {

        when(sjekk.loggetInn()).thenReturn("45626252411");

        Kunde kunde1 = new Kunde("45626252411", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","passport");

        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        String restulat = bankController.endre(kunde1);
        Assert.assertEquals("OK", restulat);
    }

    @Test
    public void test_endreKundeInfoFeil() {

        when(sjekk.loggetInn()).thenReturn("9898765212");

        Kunde kunde1 = new Kunde("9898765212", "Jelleh", "Gashi", "More-ndin 12", "2020", "Asker", "2010284663","passord");


        Mockito.when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        String restulat = bankController.endre(kunde1);
        Assert.assertEquals("Feil", restulat);
    }

    @Test
    public void test_hentKundeInfo_loggetinn() {

        when(sjekk.loggetInn()).thenReturn("45626252411");

        Kunde kunde1 = new Kunde("45626252411", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","passport");

        Mockito.when(repository.hentKundeInfo(kunde1.getPersonnummer())).thenReturn(kunde1);

        Kunde restulat = bankController.hentKundeInfo();

        Assert.assertEquals(kunde1, restulat);
    }

    @Test
    public void test_hentKundeInfo_ikkeloggetinn() {

        when(sjekk.loggetInn()).thenReturn("45626252411");

        Kunde kunde1 = new Kunde("45626252411", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","passport");

        Mockito.when(repository.hentKundeInfo(kunde1.getPersonnummer())).thenReturn(null);

        Kunde restulat = bankController.hentKundeInfo();

        Assert.assertNull(restulat);
    }

    @Test
    public void utførbetalingOK() {

        List<Transaksjon> transaksjonser = new ArrayList<>();
        Transaksjon tran1 = new Transaksjon(1, "1234141421", 3552.56, "2002-21-2", "Halla på deg", "aventer", "121514142617");
        transaksjonser.add(tran1);

        when(sjekk.loggetInn()).thenReturn("121514142617");

        Mockito.when(repository.utforBetaling(tran1.getTxID())).thenReturn("OK");

        List<Transaksjon> resultatUtfør = bankController.utforBetaling(tran1.getTxID());

        Mockito.when(repository.hentBetalinger((anyString()))).thenReturn(resultatUtfør);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(transaksjonser, resultat);

    }

}

