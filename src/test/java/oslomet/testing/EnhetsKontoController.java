package oslomet.testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetsKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController kontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_registrerkonto() {

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.registrerKonto(konto1)).thenReturn("OK");

        String restulat = kontoController.registrerKonto(konto1);
        Assert.assertEquals("OK", restulat);
    }

    @Test
    public void test_registrerkonto_Ikkeloggetinn() {

        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(repository.registrerKonto(konto1)).thenReturn(null);

        String restulat = kontoController.registrerKonto(konto1);
        Assert.assertEquals("Ikke innlogget", restulat);
    }

    @Test
    public void test_hentalleKonti() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("105010123456");

        liste.add(konto1);

        when(repository.hentAlleKonti()).thenReturn(liste);

        List<Konto> restulat = kontoController.hentAlleKonti();
        Assert.assertEquals(liste, restulat);
    }

    @Test
    public void test_hentalleKontiFeil() {

     when(repository.hentAlleKonti()).thenReturn(null);

        List<Konto> restulat = kontoController.hentAlleKonti();
        Assert.assertNull(restulat);
    }

    @Test
    public void test_endrekonto() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        liste.add(konto1);

        when(repository.endreKonto(konto1)).thenReturn("OK");

        String restulat = kontoController.endreKonto(konto1);
        Assert.assertEquals("OK", restulat);
    }

    @Test
    public void test_endrekonto_ikkeloggetinn() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        liste.add(konto1);

        when(sjekk.loggetInn()).thenReturn(null);
        when(repository.registrerKonto(konto1)).thenReturn(null);

        String restulat = kontoController.endreKonto(konto1);
        Assert.assertEquals("Ikke innlogget", restulat);
    }

    @Test
    public void test_slettkonto() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn("105010123456");
        liste.add(konto1);

        when(repository.slettKonto(konto1.getKontonummer())).thenReturn("OK");

        String restulat = kontoController.slettKonto(konto1.getKontonummer());
        Assert.assertEquals("OK", restulat);
    }

    @Test
    public void test_slettkonto_ikkeloggetinn() {
        List<Konto> liste = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(null);
        liste.add(konto1);

        when(repository.slettKonto(konto1.getKontonummer())).thenReturn(null);

        String restulat = kontoController.slettKonto(konto1.getKontonummer());
        Assert.assertEquals("Ikke innlogget", restulat);
    }
}
