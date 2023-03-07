package oslomet.testing;

import com.fasterxml.jackson.databind.DatabindContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetsSikkerhetController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Autowired
    private HttpSession session;



    @Test
    public void sjekkLoggInnOK() {

        Kunde kunde1 = new Kunde("12345654321", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","ylli1105");

        when(sjekk.loggetInn()).thenReturn("12345654321");


        Mockito.when(repository.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("OK");

        String resultat = sjekk.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord());

        assertEquals("OK", resultat);


    }

}
