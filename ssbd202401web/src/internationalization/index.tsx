import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import { LanguageType } from "../types/enums/LanguageType.enum";
import { initValidation } from "../validation/schemas";

// const language = {
//   POLISH: {
//     profileDetailsHeading: "Szczegóły profilu",
//     tableKey: "Klucz",
//     tableValue: "Wartość",
//     tableUsername: "Nazwa użytkownika",
//     tableRoles: "Role",
//     tableFirstName: "Imię",
//     tableLastName: "Nazwisko",
//     tableGender: "Płeć",
//     tableIsActive: "Czy aktywny",
//     tableIsVerified: "Czy zweryfikowany",
//     tableIsUnlocked: "Czy odblokowany",
//     tableLanguagePreference: "Preferencje językowe",
//     eventsLink: "Wydarzenia",
//   },
//   ENGLISH: {
//     profileDetailsHeading: "Profile details",
//     tableKey: "Key",
//     tableValue: "Value",
//     tableUsername: "Username",
//     tableRoles: "Password",
//     tableFirstName: "First name",
//     tableLastName: "Last name",
//     tableGender: "Gender",
//     tableIsActive: "Is active",
//     tableIsVerified: "Is verified",
//     tableIsUnlocked: "Is unlocked",
//     tableLanguagePreference: "Language preference",
//     eventsLink: "Events",
//   },
// };

i18next
  .use(initReactI18next)
  .init({
    resources: {
      POLISH: {
        translation: {
          accountsLink: "Konta",
          eventsLink: "Wydarzenia",
          logInLink: "Zaloguj się",
          registerLink: "Rejestracja",
          profileLink: "Profil",
          logOutLink: "Wyloguj",
          myTicketsLink: "Moje bilety",
          ticketLink: "Ticket",
          ticketStatus: "Status biletu",
          locationsLink: "Lokalizacje",
          speakersLink: "Prowadzący",

          roles: "Role",
          role: "Rola",
          userName: "Nazwa konta",
          firstName: "Imię",
          lastName: "Nazwisko",
          gender: "Płeć",
          isActive: "Aktywne",
          isVerified: "Zweryfikowe",
          isUnlocked: "Odblokowane",
          verify: "Potwierdź",
          theme: "Motyw",
          timeZone: "Strefa czasowa",
          createdAt: "Data utworzenia",
          updatedAt: "Data aktualizacji",
          createdBy: "Utworzone przez",
          updatedBy: "Zaktualizowane przez",
          actionType: "Typ akcji",
          eventPage: "Strona wydarzenia",

          tableKey: "Klucz",
          tableValue: "Wartość",
          eventsHeading: "Wydarzenia",
          languagePref: "Preferencje językowe",
          lastSuccLogin: "Ostatnie poprawne logowanie",
          lastSuccLoginIP: "Adres IP ostatniego poprawnego logowania",
          lastFailedLogin: "Ostatnie niepoprawne logowanie",
          lastFailedLoginIP: "Adres IP ostatniego niepoprawnego logowania",
          failedLoginAttempts: "Nieudane próby logowania",
          lockedUntil: "Zablokowane do",
          notLocked: "Nie zablokowane",

          yes: "Tak",
          no: "Nie",
          never: "Nigdy",
          save: "zapisz",
          welcome: "Witaj",
          saveChanges: "zapisz zmiany",
          action: "Akcja",
          refreshData: "Odśwież Dane",
          home: "Strona główna",
          accounts: "Konta",
          accountDetails: "Szczegóły konta",
          gotoHomePage: "Wróć do strony głównej",
          confEmailChange: "Potwierdź swoją zmiane adresu email!",
          confUnblockAccount: "Potwierdź odblokowanie konta!",
          clickToConfEmail:
            "Naciśnij przycisk poniżej, aby potwierdzić zmiane email!",
          clickToConfUnblockAccount:
            "Naciśnij przycisk poniżej, aby potwierdzić odblokowanie konta!",
          emailHasBeenChanged: "Adres email został zmieniony!",
          accountHasBeenUnblocked: "Konto zostało odblokowane",

          POLISH: "Polski",
          ENGLISH: "Angielski",

          ROLE_ADMIN: "Administrator",
          ROLE_PARTICIPANT: "Uczestnik",
          ROLE_MANAGER: "Zarządca",

          UPDATE: "Aktualizacja",
          CREATE: "Utworzenie",

          changePersonalDataTitle: "Zmień dane personalne",
          changePersonalDataBody: "Zmień swoje dane personalne!",
          changePassword: "Zmień hasło",
          changeEmail: "Zmień adres e-mail",
          enterNewMail: "Wprowadź nowy adres e-mail poniżej",
          newEmail: "Nowy adres e-mail",
          newPassword: "Nowe hasło",
          confirmNewPassword: "Potwierdz nowe hasło",
          changeAccStatus: "Zmień status konta",
          activeDeactiveAcc: "Aktywuj lub dezaktywuj konto!",
          clickButBToChangePass:
            "Naciśnij poniższy przycisk, aby wysłać maila z linkiem do zmiany hasła!",
          enterNewPersonalData: "Podaj nowe dane personalne konta!",
          changeAccRole: "Zmień role konta",
          assignRemoveRoles: "Nadaj lub odbierz role!",
          addSpeakerSuccess: "Pomyślnie dodano prowadzącego!",
          addRole: "Nadaj rolę",
          addSpeakerText: "Dodaj prowadzącego",
          chooseOneOfRoles: "Wybierz jedną z dostępnych ról do nadania!",
          rolesAssignedToAcc: "Role przypisane do konta",
          currentPassword: "Aktulane hasło",
          areYouSure: "Czy jesteś pewnien, że chcesz kontynuować?",
          confirmChange: "Potwierdź zmiane",
          profileDetails: "Dane konta",
          changeProfileDetails: "Zmień dane konta",
          accountChanges: "Zmiany konta",
          manageAccounts: "Zarządzaj kontami",
          somethingHappened: "Stało się coś złego :((",
          errorConfPassword:
            "Wystąpił błąd podczas potwierdzania konta, jeżeli uważasz, że to pomyłka, skontaktuj się ze wsparciem",
          errorConfUnblockAccount:
            "Wystąpił błąd podczas potwierdzania odblokowania konta, jeżeli uważasz, że to pomyłka, skontaktuj się ze wsparciem",
          clickToConfPassChange:
            "Naciśnij przycisk na dole, aby potwierdzić zmiane hasła!",
          passwordHasBeenChanged: "Hasło zostało zmienione!",
          youCanGoToLogin: "Możesz samemu przejść na strone logowania, lub ",
          goBackToLoginPage: "Wróć do strony głównej",

          active: "Aktywne",
          inactive: "Nieaktywne",

          NotKnown: "Nieznana",
          Male: "Mężczyzna",
          Female: "Kobieta",
          NotSpecified: "Nie podano",

          noURLParam: "Brak parametru w adresie URL",
          clickHere: "kliknij tutaj!",
          homeHeading: "Strona główna",
          locationHeading: "Strona lokalizacji",
          locationsHeading: "Strona z lokalizacjami",
          usernameLabel: "Nazwa użytkownika",
          passwordLabel: "Hasło",
          speakers: "Prowadzący",
          forgotPasswordLabel: "Zapomniałem hasła :(",
          signInLabel: "Nie posiadasz konta? Zarejestruj się!",
          resetPasswordLabel: "Zresetuj hasło",
          confirmPasswordLabel: "Powtórz hasło",
          haveAccountLabel: "Masz już konto? Zaloguj się!",
          speakerHeading: "Strona prowadzącego",
          speakersHeading: "Strona z prowadzącymi",
          errorVerifyAccount:
            "Wystąpił błąd podczas potwierdzania konta, jeżeli uważasz, że to pomyłka, skontaktuj się ze wsparciem",
          verifyAccountHeading: "Potwierdź swoje konto!",
          verifyAccountButton:
            "Wciśnij ten przycisk aby potwierdzić swoje konto!",
          verifiedAccount: "Konto zostało potwierdzone!",
          logInSucc: "Zalogowano pomyślnie! Witaj, ",
          logInFail: "Nie udało się zalogować :(",
          logOutSucc: "Wylogowano pomyślnie! Do zobaczenia :)",
          logOutFail: "Nie udało się wylogować :(",
          signInSucc:
            "Zarejestrowano pomyślnie! Sprawdź swoją skrzynkę mailową i zweryfikuj swoje konto!",
          signInFail: "Nie udało się zarejestrować :(",
          verifyAccountSucc: "Konto potwierdzone pomyślnie!",
          verifyAccountFail: "Nie udało się potwierdzić konta :(",
          changePasswordSucc: "Hasło zmienione pomyślnie!",
          changePasswordFail: "Nie udało się zmienić hasła :(",
          changeEmailSucc: "E-mail zmieniony pomyślnie!",
          confirmUnblockAccSucc: "Konto odblokowane pomyślnie!",
          changeEmailFail: "Nie udało się zmienić maila :(",
          confirmUnblockAccFail: "Nie udało się odblokować konta",
          getMyAccountFail: "Nie udało się zaktualizować konta :(",
          updateMyPersonalDataSucc: "Dane personalne zmienione pomyślnie!",
          updateMyPersonalDataFail:
            "Nie udało się zmienić danych personalnych :(",
          updateMyPasswordSucc:
            "Kod do potwierdzenia nowego hasła został wysłany na twojego maila!",
          updateMyPasswordFail: "Nie udało się zmienić hasła :(",
          updateMyEmailSucc:
            "Sprawdź swoją skrzynkę mailową i potwierdź zmianę maila!",
          updateMyEmailFail: "Nie udało się zmienić maila :(",
          requestPasswordResetSucc:
            "Sprawdź skrzynkę mailową i zresetuj hasło!",
          requestPasswordResetFail: "Nie udało się zresetować hasła :(",
          resetMyPasswordSucc: "Hasło zresetowane pomyślnie!",

          getAllAccountFail: "Nie udało się pobrać wszystkich kont :(",
          getAccountByUsernameFail: "Nie udało się pobrać użytkownika :(",
          getAccountChangesFail: "Nie udało się pobrać zmian konta :(",
          updateAccountDataSucc: "Dane zaktualizowane pomyślnie!",
          updateAccountDataFail: "Nie udało się zaktualizować danych :(",
          updateAccountPasswordSucc: "Pomyślne rozpoczęcie resetu hasła!",
          updateAccountPasswordFail: "Nie udało się zresetować danych :(",
          updateAccountEmailSucc: "E-mail zmieniony pomyślnie!",
          updateAccountEmailFail: "Nie udało się zmienić adresu e-mail :(",
          activateAccountSucc: "Konto uaktywnione pomyślnie!",
          activateAccountFail: "Nie udało się aktywować konta :(",
          activateDeaccountSucc: "Konto dezaktywowane pomyślnie!",
          activateDeaccountFail: "Nie udało się dezaktywować konta :(",
          addRoleSucc: "Rola przyznana pomyślnie!",
          addRoleFail: "Nie udało się przyznać roli :(",
          removeRoleSucc: "Rola odebrana pomyślnie!",
          removeRoleFail: "Nie udało się odebrać roli :(",
          signInHeading: "Zarejestruj się!",
          searchPhrase: "Szukaj frazy po imieniu lub nazwisku",
          "e-mail": "E-mail",
          id: "ID",
          ascending: "Rosnąco",
          descending: "Malejąco",
          rowsPerPage: "Ilość wierszy na stronę",
          displayedRows: "Wyświetlono",
          of: "z",
          filterByPhraseTitle: "Filtrowanie danych po frazie",
          filterByPhraseBody: "Filtruj dane po imieniu i nazwisku",
          sortDataKeyTitle: "Sortuj dane po kluczu",
          sortDataKeyBody: "Wybierz klucz, po którym dane mają być sortowane",
          sortDataDirTitle: "Kierunek sortowania",
          sortDataDirBody: "Wybierz kolejność sortowania",
          speakerDetails: "Szczegóły prowadzących",
          manageSpeakers: "Zarządzaj prowadzącymi",
          updateSpeakerByIdFail: "Nie udało się zaktualizować prowadzącego",
          filterData: "Filtruj dane w tabeli",
          filterKey: "Klucz",
          "OPTLCKE.": "Wczytane dane są nieaktualne. Odśwież je!",
          "Role with given name does not exist.":
            "Rola o tej nazwie nie istnieje",
          "Role  is already assigned.": "Konto już posiada tę rolę",
          "Participant can't have other roles.":
            "Uczestnik nie może mieć innych ról",
          "This account does not have this role.":
            "To konto nie posiada tej roli",
          "Account not found.": "Konto nie istnieje",
          "Email already exists.": "Konto z tym adresem e-mail już istnieje",
          "Token has expired.": "Żeton wygasł",
          "Token not found.": "Żeton nie istnieje",
          "Token has been used.": "Żeton został już wykorzystany",
          "Confirmation token has expired.": "Żeton do potwierdzenia wygasł",
          "Confirmation token not found.":
            "Żeton do potwierdzenia nie istnieje",
          "This password already was set in history.":
            "To hasło już było przypisane do konta",
          "Old password is not correct.": "Aktualne hasło jest niepoprawne",
          "Email reset token not found.":
            "Żeton do resetu adresu e-mail nie istnieje",
          "Incorrect email.": "E-mail jest niepoprawny",
          "Incorrect password.": "Hasło jest niepoprawne",
          "Incorrect username.": "Nazwa użytkownika jest niepoprawna",
          "Incorrect gender.": "Płeć jest niepoprawna",
          "Incorrect first name.": "Imię jest niepoprawne",
          "Incorrect last name.": "Nazwisko jest niepoprawne",
          "Incorrect language.": "Język jest niepoprawny",
          "Theme not found.": "Motyw nie istnieje",
          "Unlock token not found.": "Żeton do odblokowania konta nie istnieje",
          "Time zone not found.": "Strefa czasowa nie istnieje",
          "Account is locked.": "Konto jest zablokowane",
          "User account is locked": "Konto jest zablokowane",
          "Account is not verified.": "Konto nie jest zweryfikowane",
          "Resource not found.": "Zasób nie istnieje",
          "Bad request.": "Żądanie jest niepoprawne",
          "Unprocessable entity.": "Zasób nie może być przetworzony",
          "Forbidden.": "Odmowa dostępu",
          "Precondition failed.": "Warunek wstępny nie został spełniony",
          "Entity mapping error.": "Wystąpił błąd po stronie serwera",
          "Entity annotation error.": "Wystąpił błąd po stronie serwera",
          "Problem with hibernate.": "Wystąpił błąd po stronie serwera",
          "DB connection error.": "Wystąpił błąd po stronie serwera",
          "Entity lazy initialization error.":
            "Wystąpił błąd po stronie serwera",
          usernameTooShort: "Nazwa użytkownika musi mieć minimum 3 znaki",
          usernameTooLong:
            "Nazwa użytkownika nie może być dłuższa niż 32 znaki",
          usernameWrongFormat:
            "Nazwa użytkownika może zawierać tylko znaki alfanumeryczne",
          usernameRequired: "Nazwa użytkownika jest wymagana",
          passwordTooShort: "Hasło musi mieć minimum 8 znaków",
          passwordTooLong: "Hasło nie możę być dłuższe niż 72 znaki",
          passwordWrongFormat:
            "Hasło musi zawierać przynajmniej 1 wielką literę, 1 małą literę i 1 znak specjalny",
          passwordRequired: "Hasło jest wymagane",
          passwordsDontMatch: "Hasła się nie zgadzają",
          confirmPasswordRequired: "Powtórzenie hasła jest wymagane",
          emailWrongFormat: "Adres e-mail nie jest w poprawnym formacie",
          emailRequired: "Adres e-mail jest wymagany",
          firstNameTooShort: "Imię musi mieć minimum 2 znaki",
          firstNameTooLong: "Imię nie może być dłuższe niż 32 znaki",
          firstNameWrongFormat: "Imię może zawierać tylko znaki alfanumeryczne",
          firstNameRequired: "Imię jest wymagane",
          lastNameTooShort: "Nazwisko musi mieć minimum 2 znaki",
          lastNameTooLong: "Nazwisko nie może być dłuższe niż 64 znaki",

          lastNameWrongFormat:
            "Nazwisko może zawierać tylko znaki alfanumeryczne",
          lastNameRequired: "Nazwisko jest wymagane",
          newPasswordDifferentThanOld: "Nowe hasło musi być inne, niż stare",
          "Europe/London": "Europa/Londyn",
          "Europe/Warsaw": "Europa/Warszawa",
          getMyThemeFail:
            "W prawym górnym rogu możesz zmienić motyw, jeśli potrzebujesz :)",
          setMyThemeFail: "Nie udało się ustawić motywu :(",
          notSpecified: "Nie określono",
          Light: "Jasny",
          Dark: "Ciemny",
          refreshModalTitle: "Twoja sesja niedługo wygaśnie!",
          refreshModalBody:
            "Twoja sesja wygaśnie w mniej niż 60 sekund. Odśwież ją lub zignoruj tę informację przy pomocy jedenego z przycisków!",

          "0": "Nie określona",
          "1": "Mężczyzna",
          "2": "Kobieta",
          "9": "Nie podano",
          refresh: "Odśwież",
          cancel: "Anuluj",
          refreshSucc: "Sesja została odswieżona!",
          refreshFail: "Nie udało się odświeżyć sesji :(",
          username: "Nazwa użytkownika",
          email: "Adres e-mail",
          language: "Język",
          confirm: "Potwierdź",
          deny: "Odrzuć",
          confPassChange: "Potwierdź zmianę hasła",
          confirmSignIn: "Potwierdź rejestrację",
          signInSummary: "Podsumowanie rejestracji",
          getLocationsFail: "Nie udało się pobrać lokalizacji :(",
          locations: "Lokalizacje",
          name: "Nazwa",
          city: "Miasto",
          country: "Kraj",
          street: "Ulica",
          buildingNumber: "Numer budynku",
          postalCode: "Kod pocztowy",
          locationDetails: "Szczegóły lokalizacji",
          manageLocations: "Zarządzaj lokalizacjami",
          changeLocationDetails: "Zarządzaj lokalizacją",
          enterNewLocationData: "Wprowadź nowe dane lokalizacji!",
          addLocation: "Dodaj lokalizację",
          submit: "Zatwierdź",
          addLocationFail: "Nie udało się dodać lokalizacji :(",
          addLocationSuccess: "Lokalizacja dodana pomyślnie!",
          nameTooShort: "Nazwa musi mieć minimum 3 znaki",
          nameTooLong: "Nazwa nie może być dłuższa niż 128 znaków",
          nameRequired: "Nazwa jest wymagana",
          cityWrongFormat: "Miasto może zawierać tylko znaki alfanumeryczne",
          cityRequired: "Miasto jest wymagane",
          countryWrongFormat: "Kraj może zawierać tylko znaki alfanumeryczne",
          countryRequired: "Kraj jest wymagany",
          streetWrongFormat: "Ulica może zawierać tylko znaki alfanumeryczne",
          streetRequired: "Ulica jest wymagana",
          buildingNumberWrongFormat:
            "Numer budynku może zawierać tylko znaki alfanumeryczne",
          buildingNumberRequired: "Numer budynku jest wymagany",
          postalCodeWrongFormat: "Kod pocztowy musi być w formacie XX-XXX",
          postalCodeRequired: "Kod pocztowy jest wymagany",
          upcomingSessions: "Nadchodzące sesje",
          history: "Historia",
          sessionName: "Nazwa sesji",
          startTime: "Data rozpoczęcia",
          endTime: "Data zakończenia",
          locationName: "Lokacja",
          roomName: "Numer pomieszczenia",
          updateLocationSuccess: "Pomyślnie zaktualizowano lokalizację",
          signDate: "Data zapisu",
          sessionEndDate: "Data zakończenia sesji",
          sessionStartDate: "Data rozpoczęcia sesji",
          sessionStatus: "Status sesji",
          eventName: "Nazwa wydarzenia",
          buildingAddress: "Adres budynku",
          speaker: "Prowadzący",
          ticketStatusSigned: "Zapisany",
          ticketStatusUnSigned: "Wypisano z sesji",
          sessionStatusActive: "Sesja w harmonogramie wydarzenia",
          sessionStatusNotActive: "Sesja anulowana",
          deleteLocation: "Usuń lokalizację",
          addSpeakerFail: "Nie udało się dodać prowadzącego :(",
          updateSpeakerSuccess: "Pomyślnie zaktualizowano prowadzącego",
          deleteLocationSuccess: "Pomyślnie usunięto lokalizację",
          deleteLocationByIdFail: "Nie udało się usunąć lokalizacji",
          clickButToDelLocation:
            "Naciśnij przycisk poniżej, aby usunąć lokalizację!",
          sortData: "Sortuj dane w tablicy",
          manageRooms: "Zarządzaj pokojami",
          addEvent: "Dodaj wydarzenie",
          eventNameLabel: "Nazwa",
          eventDescLabel: "Opis",
          startDateLabel: "Data rozpoczęcia",
          endDateLabel: "Data zakończenia",
          eventNameTooShort: "Nazwa wydarzenia musi mieć minimum 3 znaki",
          eventNameTooLong:
            "Nazwa wydarzenia nie może być dłuższa niż 128 znaków",
          eventNameRequired: "Nazwa wydarzenia jest wymagana",
          eventDescTooShort: "Opis wydarzenia musi mieć minimum 3 znaki",
          eventDescTooLong:
            "Opis wydarzenia nie może być dłuższy niż 1024 znaki",
          eventDescRequired: "Opis wydarzenia jest wymagany",
          addEventLabel: "Dodaj wydarzenie",
          createEventSucc: "Wydarzenie utworzono pomyślnie!",
          createEventFail: "Nie udało się utworzyć wydarzenia :(",
          eventRedirectQuestion: "Czy chciałbyś dodać sesje od razu?",
        },
      },
      ENGLISH: {
        translation: {
          accountsLink: "Accounts",
          eventsLink: "Events",
          logInLink: "Log in",
          registerLink: "Sign in",
          profileLink: "Profile",
          logOutLink: "Log-out",
          myTicketsLink: "My tickets",
          ticketLink: "Ticket",
          ticketStatus: "Ticket status",
          locationsLink: "Locations",
          speakersLink: "Speakers",

          roles: "Roles",
          role: "Role",
          userName: "Username",
          firstName: "First Name",
          lastName: "Last Name",
          gender: "Gender",
          isActive: "Is Active",
          isVerified: "Is Verified",
          isUnlocked: "Is Unlocked",
          verify: "Verify",
          theme: "Theme",
          timeZone: "Time zone",
          createdAt: "Creation date",
          updatedAt: "Update date",
          createdBy: "Created by",
          updatedBy: "Updated by",
          actionType: "Action type",
          eventPage: "Event Page",

          tableKey: "Key",
          tableValue: "Value",
          eventsHeading: "Events",
          languagePref: "Language preference",
          lastSuccLogin: "Last successful login",
          lastSuccLoginIP: "Last successful login IP",
          lastFailedLogin: "Last failed login",
          lastFailedLoginIP: "Last failed login IP",
          failedLoginAttempts: "Failed login attempts",
          lockedUntil: "Locked until",
          notLocked: "Not Locked",

          yes: "Yes",
          no: "No",
          never: "Never",
          save: "save",
          welcome: "Welcome",
          saveChanges: "Save changes",
          action: "Action",
          refreshData: "Refresh Data",
          home: "Home",
          accounts: "Accounts",
          accountDetails: "Account details",
          gotoHomePage: "Go to home page",
          confEmailChange: "Confirm your email change!",
          confUnblockAccount: "Confirm unblocking account!",
          confPassChange: "Confirm your password change!",
          clickToConfEmail:
            "Click the button below to confirm your email change!",
          clickToConfUnblockAccount:
            "Click the button below to confirm unblocking account!",
          emailHasBeenChanged: "Email has been changed!",
          accountHasBeenUnblocked: "Account has been unblocked",

          POLISH: "Polish",
          ENGLISH: "English",

          ROLE_ADMIN: "Administrator",
          ROLE_PARTICIPANT: "Participant",
          ROLE_MANAGER: "Manager",

          UPDATE: "Update",
          CREATE: "Create",

          changePersonalDataTitle: "Change personal data",
          changePersonalDataBody: "Change your personal data!",
          changePassword: "Change password",
          changeEmail: "Change e-mail",
          enterNewMail: "Enter new e-mail address below!",
          newEmail: "New e-mail",
          newPassword: "New password",
          confirmNewPassword: "Confirm new password",
          changeAccStatus: "Change account's status",
          activeDeactiveAcc: "Activate or deactivate user's account!",
          clickButBToChangePass:
            "Click the button below to send an e-mail with password reset link!",
          enterNewPersonalData: "Enter account's new personal data below!",
          changeAccRole: "Change account's roles",
          assignRemoveRoles: "Assing or remove account's roles!",
          addRole: "Add role",
          chooseOneOfRoles: "Choose one of the available roles to add!",
          rolesAssignedToAcc: "Roles assigned to the account",
          currentPassword: "Current password",
          areYouSure: "Are you sure you want to procceed?",
          confirmChange: "Confirm change",
          profileDetails: "Profile details",
          changeProfileDetails: "Change profile details",
          accountChanges: "Account changes",
          addSpeakerText: "Add speaker",
          manageSpeakers: "Manage speakers",

          manageAccounts: "Manage accounts",
          somethingHappened: "Something bad happened :((",
          errorConfPassword:
            "There was an error while confirming your email change. If you think this is a mistake, contact support.",
          errorConfUnblockAccount:
            "There was an error while confirming unblocking account. If you think this is a mistake, contact support.",
          clickToConfPassChange:
            "Click the button below to confirm your password change!",
          passwordHasBeenChanged: "Password has been changed!",
          youCanGoToLogin: "You can go to login page manually, or ",
          goBackToLoginPage: "Go back to login page",

          active: "Active",
          inactive: "Inactive",

          NotKnown: "Not Known",
          Male: "Male",
          Female: "Female",
          NotSpecified: "Not Specified",

          noURLParam: "No URL parameter has been provided",
          clickHere: "click here!",
          homeHeading: "Home page",
          locationHeading: "Location page",
          locationsHeading: "Locations page",
          usernameLabel: "Username",
          passwordLabel: "Password",
          forgotPasswordLabel: "I forgot my password :(",
          signInLabel: "You don't have an account yet? Sign in!",
          resetPasswordLabel: "Reset your password",
          confirmPasswordLabel: "Confirm password",
          addSpeakerSuccess: "Speaker has been added successfully!",
          addSpeakerFail: "Failed to add a speaker :(",
          haveAccountLabel: "Already have an account? Log in!",
          speakerHeading: "Speaker page",
          speakers: "Speakers",
          speakersHeading: "Speakers page",
          errorVerifyAccount:
            "There was an error while verifying your account. If you think this is a mistake, contact support.",
          verifyAccountHeading: "Verify your account!",
          verifyAccountButton: "Click the button below to verify your account!",
          verifiedAccount: "Account has been confirmed!",
          logInSucc: "Successfully logged in! Welcome, ",
          logInFail: "Failed to log in :(",
          logOutSucc: "Successfully logged out! See you soon :)",
          logOutFail: "Failed to log out :(",
          signInSucc:
            "Successfully signed in! Check your e-mail box and verify your account!",
          signInFail: "Signing in failed :(",
          verifyAccountSucc: "Account has been confirmed successfully!",
          verifyAccountFail: "Failed to verify an account :(",
          updateSpeakerSuccess: "Speaker has been updated successfully!",
          updateSpeakerByIdFail: "Failed to update a speaker",
          changePasswordSucc: "Password has been changed!",
          changePasswordFail: "Failed to change a password :(",
          changeEmailSucc: "Email has been verified!",
          confirmUnblockAccSucc: "Account has been unblocked",
          changeEmailFail: "Failed to verify email :(",
          confirmUnblockAccFail: "Failed to unblock account",
          getMyAccountFail: "Failed to fetch an account :(",
          updateMyPersonalDataSucc: "Account has been updated!",
          updateMyPersonalDataFail: "Account update failed :(",
          updateMyPasswordSucc: "Email with confirmation has been sent!",
          updateMyPasswordFail: "Password change failed :(",
          updateMyEmailSucc: "Email sent! Confirm your email change",
          updateMyEmailFail: "Email change failed :(",
          requestPasswordResetSucc:
            "Request sent successfully! Check your mailbox!",
          requestPasswordResetFail: "Password reset failed :(",
          resetMyPasswordSucc: "Password has been reset successfully!",
          speakerDetails: "Speaker details",
          getAllAccountFail: "Failed to fetch all users :(",
          getAccountByUsernameFail: "Failed to fetch user :(",
          getAccountChangesFail: "Failed to fetch account changes :(",
          updateAccountDataSucc: "Data updated successfully!",
          updateAccountDataFail: "Failed to update data :(",
          updateAccountPasswordSucc: "Password reset started successfully!",
          updateAccountPasswordFail: "Failed to reset password :(",
          updateAccountEmailSucc: "E-mail changed successfully!",
          updateAccountEmailFail: "Failed to change email :(",
          activateAccountSucc: "Account activated successfully!",
          activateAccountFail: "Failed to activate account :(",
          activateDeaccountSucc: "Account deactivated successfully!",
          activateDeaccountFail: "Failed to deactivate account :(",
          addRoleSucc: "Role assinged successfully!",
          addRoleFail: "Failed to assing role :(",
          removeRoleSucc: "Role removed successfully!",
          removeRoleFail: "Failed to remove role :(",
          signInHeading: "Sign in!",
          searchPhrase: "Search phrase by first or last name",
          "e-mail": "E-mail",
          id: "ID",
          ascending: "Ascending",
          descending: "Descending",
          rowsPerPage: "Rows per page",
          displayedRows: "Viewed",
          of: "of",
          filterByPhraseTitle: "Filter data by phrase",
          filterByPhraseBody: "Filter data by first and last name",
          sortDataKeyTitle: "Sort data by key",
          sortDataKeyBody: "Choose a key, by which data will be sorted",
          sortDataDirTitle: "Sort direction",
          sortDataDirBody: "Choose sort direction",
          filterData: "Filter data in a table",
          filterKey: "Key",
          "OPTLCKE.": "Data you try to update is out of date. Refresh it!",
          "Role with given name does not exist.":
            "Role with given name does not exist",
          "Role  is already assigned.": "Role is already assigned",
          "Participant can't have other roles.":
            "Participant can't have other roles",
          "This account does not have this  role.":
            "This account does not have this role",
          "Account not found.": "Account doesn't exist",
          "Email already exists.":
            "An account with this e-mail address already exists",
          "Token has expired.": "Token has expired",
          "Token not found.": "Token not found",
          "Token has been used.": "Token has been used",
          "Confirmation token has expired.": "Confirmation token has expired",
          "Confirmation token not found.": "Confirmation token not found",
          "This password already was set in history.":
            "This password already was set in history",
          "Old password is not correct.": "Old password is incorrect",
          "Email reset token not found.": "Email reset token not found",
          "Incorrect email.": "E-mail address is incorrect",
          "Incorrect password.": "Password is incorrect",
          "Incorrect username.": "Username is incorrect",
          "Incorrect gender.": "Gender is incorrect",
          "Incorrect first name.": "First name is incorrect",
          "Incorrect last name.": "Last name is incorrect",
          "Incorrect language.": "Language is incorrect",
          "Theme not found.": "Theme doesn't exist",
          "Unlock token not found.": "Unlock token not found",
          "Time zone not found.": "Time zone doesn't exist",
          "Account is locked.": "Account is locked",
          "User account is locked": "Account is locked",
          "Account is not verified.": "Account is not verified",
          "Resource not found.": "Resource not found",
          "Bad request.": "Bad request",
          "Unprocessable entity.": "Unprocessable entity",
          "Forbidden.": "Access denied",
          "Precondition failed.": "Internal server error",
          "Entity mapping error.": "Internal server error",
          "Entity annotation error.": "Internal server error",
          "Problem with hibernate.": "Internal server error",
          "DB connection error.": "Internal server error",
          "Entity lazy initialization error.": "Internal server error",
          usernameTooShort: "Username must be at least 3 characters long",
          usernameTooLong: "Username can be a maximum of 32 characters",
          usernameWrongFormat: "Username can contain only alphanumeric values",
          usernameRequired: "Username is required",
          passwordTooShort: "Password must be at least 8 characters long",
          passwordTooLong: "Password can be a maximum of 32 characters",
          passwordRequired: "Password is required",
          passwordWrongFormat:
            "Password must contain a 1 capital letter, 1 lowercase letter and 1 special character",
          passwordsDontMatch: "Password's don't match",
          confirmPasswordRequired: "Confirm password is required",
          emailWrongFormat: "E-mail is in a wrong format",
          emailRequired: "E-mail is required",
          firstNameTooShort: "First name must be at least 2 characters long",
          firstNameTooLong: "First name can be a maximum of 32 characters",
          firstNameWrongFormat:
            "First name can contain only alphanumeric values",
          firstNameRequired: "First name is required",
          lastNameTooShort: "Last name must be at least 2 characters long",
          lastNameTooLong: "Last name can be a maximum of 64 characters",
          lastNameWrongFormat: "Last name can contain only alphanumeric values",
          lastNameRequired: "Last name is required",
          newPasswordDifferentThanOld:
            "New password must be different than the old one",
          "Europe/London": "Europe/London",
          "Europe/Warsaw": "Europe/Warsaw",
          getMyThemeFail:
            "You can change your theme in the top right corner if you need :)",
          setMyThemeFail: "Failed to set theme :(",
          notSpecified: "Not specified",
          Light: "Light",
          Dark: "Dark",
          refreshModalTitle: "Your session is ending!",
          refreshModalBody:
            "Your session will expire in less than 60 seconds. Refresh it of ignore this message using one of those buttons!",

          "0": "Not Known",
          "1": "Male",
          "2": "Female",
          "9": "Not Specified",
          refresh: "Refresh",
          cancel: "Cancel",
          refreshSucc: "Session has been refreshed!",
          refreshFail: "Failed to refresh the session :(",
          username: "Username",
          email: "E-mail address",
          language: "Language",
          confirm: "Confrim",
          deny: "Deny",
          confirmSignIn: "Confirm Sign in",
          signInSummary: "Sign in summary",
          getLocationsFail: "Failed to fetch location :(",
          locations: "Locations",
          name: "Name",
          city: "City",
          country: "Country",
          street: "Street",
          buildingNumber: "Building number",
          postalCode: "Postal code",
          locationDetails: "Location details",
          manageLocations: "Manage locations",
          changeLocationDetails: "Manage location",
          upcomingSessions: "Upcoming sessions",
          history: "History",
          sessionName: "Session name",
          startTime: "Start date",
          endTime: "End date",
          locationName: "Location",
          roomName: "Room number",
          addLocation: "Add location",
          submit: "Submit",
          addLocationFail: "Failed to add location :(",
          addLocationSuccess: "Location has been added successfully!",
          nameTooShort: "Name must be at least 3 characters long",
          nameTooLong: "Name can be a maximum of 128 characters",
          nameRequired: "Name is required",
          cityWrongFormat: "City can contain only alphanumeric values",
          cityRequired: "City is required",
          countryWrongFormat: "Country can contain only alphanumeric values",
          countryRequired: "Country is required",
          streetWrongFormat: "Street can contain only alphanumeric values",
          streetRequired: "Street is required",
          buildingNumberWrongFormat:
            "Building number can contain only alphanumeric values",
          buildingNumberRequired: "Building number is required",
          postalCodeWrongFormat: "Postal code must be in format XX-XXX",
          postalCodeRequired: "Postal code is required",
          enterNewLocationData: "Enter new location data!",
          updateLocationSuccess: "Update location succces",
          signDate: "Signup date",
          sessionEndDate: "Session end date",
          sessionStartDate: "Session start date",
          sessionStatus: "Session status",
          eventName: "Event name",
          buildingAddress: "Building address",
          speaker: "Speaker",
          ticketStatusSigned: "Signed",
          ticketStatusUnSigned: "Unsigned",
          sessionStatusActive: "Session in event schedule",
          sessionStatusNotActive: "Session cancelled",
          deleteLocation: "Delete location",
          deleteLocationSuccess: "Location has been deleted successfully",
          deleteLocationByIdFail: "Failed to delete location",
          clickButToDelLocation: "Click the button below to delete location",
          sortData: "Sort data in table",
          manageRooms: "Manage rooms",
          maxCapacity: "Max capacity",
          addEvent: "Add event",
          eventNameLabel: "Name",
          eventDescLabel: "Description",
          startDateLabel: "Start date",
          endDateLabel: "End date",
          eventNameTooShort: "Event name must be at least 3 characters long",
          eventNameTooLong: "Event name can be a maximum of 128 characters",
          eventNameRequired: "Event name is required",
          eventDescTooShort:
            "Event description must be at least 3 characters long",
          eventDescTooLong:
            "Event description can be a maximum of 1024 characters",
          eventDescRequired: "Event description is required",
          addEventLabel: "Add event",
          createEventSucc: "Event has been created successfully!",
          createEventFail: "Event creation failed :(",
          eventRedirectQuestion: "Would you like to add sessions right away?",
        },
      },
    },
    lng:
      navigator.language === "pl" ? LanguageType.POLISH : LanguageType.ENGLISH,
  })
  .then(() => {
    initValidation();
  });

export default i18next;
