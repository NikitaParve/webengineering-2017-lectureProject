/**
 * Konfiguration für Sprachen (Deutsch und Englisch)
 *
 * @author Luan Hajzeraj on 12.08.2017.
 */

import i18n from "i18next";
import XHR from "i18next-xhr-backend";

// See https://www.i18next.com/ for a LOT of documentation.

i18n
    .use(XHR)
    .init({
        fallbackLng: 'en',

        // Have a common namespace used around the full app
        ns: ['common'],
        defaultNS: 'common',

        debug: false,
        wait: true,

        interpolation: {
            // not needed for react.
            escapeValue: false,
            formatSeparator: ',',
        }
    });

export default i18n;