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
    private Sikkerhet sikker;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Mock
    private BankRepository rep;

    @Mock
    private HttpSession session;



    @Test
    public void test_sjekkLoggetInnOK() {

        Kunde kunde1 = new Kunde("12345654321", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","25615552");

        Mockito.when(rep.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("OK");
        Mockito.when(sjekk.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("OK");

        String resultat = sikker.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord());

        assertEquals("OK", resultat);

    }

    @Test
    public void sjekkLoggInnFeilPersonnr() {

        Kunde kunde1 = new Kunde("123456543K", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","heipådeg");

        when(sjekk.loggetInn()).thenReturn("12345654321K");

        Mockito.when(sjekk.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("Feil i personnummer");

        String resultat = sikker.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord());

        assertEquals("Feil i personnummer", resultat);

    }

    @Test
    public void sjekkLoggInnFeilPassord() {

        Kunde kunde1 = new Kunde("12345654389", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","105");

        when(sjekk.loggetInn()).thenReturn("1234565432189");

        Mockito.when(sjekk.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("Feil i passord");

        String resultat = sikker.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord());

        assertEquals("Feil i passord", resultat);

    }

    @Test
    public void sjekkLoggInnFeilPersonnrOgPassord() {

        Kunde kunde1 = new Kunde("12345654312", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","hegsss");

        when(sjekk.loggetInn()).thenReturn("12345654321K");

        Mockito.when(sjekk.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("OK");
        Mockito.when(rep.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord())).thenReturn("Feil");
        String resultat = sikker.sjekkLoggInn(kunde1.getPersonnummer(), kunde1.getPassord());

        assertEquals("Feil i personnummer eller passord", resultat);


    }

    @Test
    public void test_LogginnAdminloggetinn() {


        Mockito.when(sjekk.loggInnAdmin("Admin","Admin")).thenReturn("Logget inn");

        String resultat = sikker.loggInnAdmin("Admin","Admin");

        assertEquals("Logget inn", resultat);


    }

    @Test
    public void test_LogginnAdminikkeloggetinn() {

        Mockito.when(sjekk.loggInnAdmin("Admina","Admina")).thenReturn("Ikke logget inn");

        String resultat = sikker.loggInnAdmin("Admins","Admina");

        assertEquals("Ikke logget inn", resultat);

    }

    @Test
    public void test_logginn() {

        Kunde kunde1 = new Kunde("12345654389", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","105");

        session.setAttribute("i", "K");
        String i = (String) session.getAttribute("i");


        assertEquals("Innlogget",i);
    }

    @Test
    public void test_logginnFeil() {

        Kunde kunde1 = new Kunde("12345654389", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","105");

        Mockito.when(sjekk.loggetInn()).thenReturn(null);
        String resultat = sikker.loggetInn();

        assertNull(resultat);

    }

    @Test
    public void test_loggut() {
        Kunde kunde1 = new Kunde("12345654389", "Illi", "Gashi", "Fare-ndin 21", "4021", "Ås", "88888888","105");

        session.setAttribute("Innlogget", kunde1.getPersonnummer());

        sikker.loggUt();
        String resultat="";
        if(session.getAttribute("Innlogget") == null){
             resultat = "Logget ut";
        }


        assertEquals("Logget ut", resultat);

    }




}
