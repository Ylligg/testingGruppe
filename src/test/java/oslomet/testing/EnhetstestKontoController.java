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
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestKontoController {


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
    public void hentTransaksjoner_loggetInn() {

        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon tran1 = new Transaksjon(1,"123456787652",2500.50, "2023-01-02", "Lommepenger", "2023-02-01", "12343212314");
        Transaksjon tran2 = new Transaksjon(1,"675151132287",200_000.99, "2023-02-01", "Egenkaptial", "2023-02-03", "671711131311");
        transaksjoner.add(tran1);
        transaksjoner.add(tran2);
        Konto konto1 = new Konto("122115116611", "717151521",36000511.61, "Sparekonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("12343212314");

        when(repository.hentTransaksjoner(tran1.getKontonummer(), tran1.getDato(), tran1.getAvventer())).thenReturn(konto1);

        Konto resultat = bankController.hentTransaksjoner(tran1.getKontonummer(), tran1.getDato(), tran1.getAvventer());


        assertEquals(konto1, resultat);

    }

    @Test
    public void hentTransaksjoner_ikkeloggetInn() {

        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon tran1 = new Transaksjon(1,"123456787652",2500.50, "2023-01-02", "Lommepenger", "2023-02-01", "12343212314");
        Transaksjon tran2 = new Transaksjon(1,"675151132287",200_000.99, "2023-02-01", "Egenkaptial", "2023-02-03", "671711131311");
        transaksjoner.add(tran1);
        transaksjoner.add(tran2);
        Konto konto1 = new Konto("122115116611", "717151521",36000511.61, "Sparekonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("12343212314");

        when(repository.hentTransaksjoner(tran1.getKontonummer(), tran1.getDato(), tran1.getAvventer())).thenReturn(konto1);


        Mockito.when(repository.hentTransaksjoner(tran1.getKontonummer(), tran1.getDato(), tran1.getAvventer())).thenReturn(null);

        // act
        Konto resultat = bankController.hentTransaksjoner(tran1.getKontonummer(), tran1.getDato(), tran1.getAvventer());

        // assert
        Assert.assertNull(resultat);

    }


}
