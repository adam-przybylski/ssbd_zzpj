import i18next from "i18next";
import { initReactI18next } from "react-i18next";
import { LanguageType } from "../types/enums/LanguageType.enum";

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

i18next.use(initReactI18next).init({
  resources: {
    POLISH: {
      translation: {
        accountsLink: "Konta",
        eventsLink: "Wydarzenia",
        logInLink: "Zaloguj się",
        registerLink: "Rejestracja",
        profileLink: "Profil",
        logOutLink: "Wyloguj",
        myEventsLink: "Moje wydarzenia",
        locationsLink: "Lokalizacje",
        speakersLink: "Prelegenci",

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
        clickToConfUnblockAccount: "Naciśnij przycisk poniżej, aby potwierdzić odblokowanie konta!",
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
        addRole: "Nadaj rolę",
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
        forgotPasswordLabel: "Zapomniałem hasła :(",
        signInLabel: "Nie posiadasz konta? Zarejestruj się!",
        resetPasswordLabel: "Zresetuj hasło",
        confirmPasswordLabel: "Powtórz hasło",
        haveAccountLabel: "Masz już konto? Zaloguj się!",
        speakerHeading: "Strona prelegenta",
        speakersHeading: "Strona z prelegentami",
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
        requestPasswordResetSucc: "Sprawdź skrzynkę mailową i zresetuj hasło!",
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
        filterData: "Filtruj dane w tabeli",
        filterKey: "Klucz",
        OPTLCKE: "Wczytane dane są nieaktualne. Odśwież je!",
        "Europe/London": "Europa/Londyn",
        "Europe/Warsaw": "Europa/Warszawa",
        getMyThemeFail:
          "W prawym górnym rogu możesz zmienić motyw, jeśli potrzebujesz :)",
        setMyThemeFail: "Nie udało się ustawić motywu :(",
        notSpecified: "Nie określono",
        Light: "Jasny",
        Dark: "Ciemny",

        '0': "Nie określona",
        "1": "Mężczyzna",
        "2": "Kobieta",
        "9": "Nie podano",
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
        myEventsLink: "My events",
        locationsLink: "Locations",
        speakersLink: "Speakers",

        roles: "Roles",
        role: "Role",
        userName: "UserName",
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
        clickToConfUnblockAccount: "Click the button below to confirm unblocking account!",
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
        changeProfileDetails: "Change personal data",
        accountChanges: "Account changes",
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
        haveAccountLabel: "Already have an account? Log in!",
        speakerHeading: "Speaker page",
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
        verifyAccountSucc: "Konto potwierdzone pomyślnie!",
        verifyAccountFail: "Failed to verify an account :(",
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
        OPTLCKE: "Data you try to update is out of date. Refresh it!",
        "Europe/London": "Europe/London",
        "Europe/Warsaw": "Europe/Warsaw",
        getMyThemeFail:
          "You can change your theme in the top right corner if you need :)",
        setMyThemeFail: "Failed to set theme :(",
        notSpecified: "Not specified",
        Light: "Light",
        Dark: "Dark",


        "0": "Not Known",
        "1": "Male",
        "2": "Female",
        "9": "Not Specified",
      },
    },
  },
  lng:
    localStorage.getItem("language") ??
    (navigator.language === "pl" ? LanguageType.POLISH : LanguageType.ENGLISH),
});
export default i18next;
