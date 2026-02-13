# projlab
Ez a repository a BME/VIK/Mérnökinformatikus képzésének  "Szoftver Projekt Laboratórium" tárgyának forráskód követésére jött létre.

1., Fejlesztésről szóló irányelvek  
  
  A jelen leírt irányelvek legnagyobb részt megegyeznek azokkal, amelyet a Google fogalmazott meg a Java nyelvben való fejlesztésről, személyesen már kipróbáltam, nyilván ízlések és pofonok, de
      biztos, hogy nem véletlenül írták le ők sem.
>     - There can not be any wildcard imports in the code, meaning:
>        import java.util.* Not good (no * can be present)
> 
>        import java.util.ArrayList Good
          
>      - First all of the static imports, then can the other imports come, meaning:
>         import java.util.List;
>         import static java.lang.Math.PI;
>         import java.util.ArrayList;
>        
>         Not good.
>        
>         import static java.lang.Math.PI;
>         import static java.lang.Math.sqrt;
>        
>        import java.util.ArrayList;
>        import java.util.List;
>          
>         Good

>       - Each top-level class resides in a source file of its own.

>       - Methods of a class that share the same name appear in a single contiguous group with no other members in between.
>         The same applies to multiple constructors. This rule applies even when modifiers such as static or private differ
>         between the methods or constructors.

>       - Braces are used with if, else, for, do and while statements, even when the body is empty or contains only
>         a single statement. (brace: '{' and '}')

>       - Line break after the opening brace.
>       - Line break before the closing brace.
>           For example:
>             try {
>               something();
>             } catch (ProblemException e) {
>               recover();
>             }

>       - Block indentation is 2 spaces!!!! (You may have gotten used to the 4 space indentation as most IDEs by default
>         use 4 spaces, however you can change it in the settings, and it grants a much more concise code in the future)

>       - Each statement is followed by a line break.
>         Pls no:
>         int asd = 1; int bfg = 2;
>         Pls do:
>         int asd = 1;
>         int bfg = 2;

>       - Column limit: 100
>         Except:
>           'Lines where obeying the column limit is not possible (for example, a long URL in Javadoc,
>            or a long JSNI method reference).
>           'package declarations and imports (see Sections 3.2 Package declarations and 3.3 Imports).
>           'Contents of text blocks.
>           'Command lines in a comment that may be copied-and-pasted into a shell.
>           'Very long identifiers

>       - Comments shall follow the javadoc pattern
> 

>       - TODO Comments shall look like this:
>         //TODO: @the_task_owner's(or_owners')_username(s) - the remaining task

>       - Class names are written in UpperCamelCase (Every starting word's first letter is capital)

>       - Method names are written in lowerCamelCase. (first word's first letter is not capital, after that every other word's
>         first letter is capital)

>       - Constant names use UPPER_SNAKE_CASE: all uppercase letters, with each word separated from the next by a single
>         underscore. (basically everything that is labeled as final in the code)

>       - Non-constant field, paramaters, and local variable names are written in lowerCamelCase.

>       - Innentől már nem google szabány:

>       - Everyone at all times shall use english notation (even in code comments and commit messages),
>         in communications hungarian is permitted and encouraged
>         (Ez a szabvány még változhat ha ki tudja milyen kukutyin magyar szavakat használó
>          programot kell írni.)

>       - BE MINDFUL WHEN USING --FORCE
>         (STRONGLY ADVISED AGAINST PLEASE ONLY USE --FORCE IF IT DOESN'T IMPACT CODE STRUCTURE ONLY TEXT
>          FIELDS COMMENTS OR README ETC... BUT NOT CODE)
 
>       - Only use google docs when signed into google, we don't want to see any "Funky Elephant" when editing it.

>       - Please when you commit make sure your username is set to your full government name, and your email is set to
>         the public email that github gives you, just like it was in "Software Engineering"'s home assignment

>       - Always make sure to pull before pushing, and resolve the conflicts you may have (linear history is encouraged, but
>         not demanded (git pull --rebase))

Persze ez nem az összes, és nem is várhatjuk el az összeset mert van pár amire nehéz odafigyelni. Ha már ezek 70%-át be tudjuk tartani minden egyes fázisnál, már akkor jók vagyunk szerintem.  
  
Workflow:  
  
  - Minden héten x és y napon zz:ww órakor tartunk 1-1 fél óra, maximum 45 perces meetinget ahol lefektetjük, hogy mikor, melyik feladatot ki fogja elvégezni.
  
  - Minden fázisnak lesz egy Product Ownere (PO), akit fázisonként váltogatunk, akinek a végső beleszólása van a feladatok kiosztásában (nem muszáj 1 feladatot 1 emberre osztani, lehet egy feladatot két (3-ra már ne) emberre is osztani)

  - A feladatokat nem kell feltétlen egyedül megoldani, de tartsuk meg az egészséges határokat a segítségkéréssel, mivel másnak is van feladata és egyeteme, illetve a feladatot úgy könyveljük el befektett órákban, mintha csakis a feladat tulajdonosa írta volna az egészet, így törekedjünk arra, hogy arányosan kérjünk segítséget.

  - Légyszíves senki se, a tárgy teljesítésének céljából, semmilyen körülmények között ne küldjön másik csapatnak forráskódot és ne is fogadjon másik csapattól forráskódot (segítséget lehet kérni, azonban van egy olyan megérzésem, hogy itt sem fogadják jó szívvel a plágiumot)

  - A csapaton belüli bürokrácia miatt ajánlom, hogy legyen egy végső csapatvezér, aki a csapatban felmerülő kétségek, vitákat tudja lerendezni, hogy ezzel se teljen idő a félév során fölöslegesen, akár ezt a szerepet váltogathatjuk is hétről hétre. EZ TODO ELSŐ MEETINGRE, HOGY LEGYEN-E VAGY SEM.

  - Minden egyes a git-beli fájlokat változtató módosítások csakis egy új feature branchen pull requesttel kérvényehetők.

2., Kapcsolattartás
>      A kapcsolattartást a messengeres csoportban nem hivatalosan, és hivatalosan discordon folytajtuk a
>      https://discord.gg/peHM3ekc meghívási linkkel rendelkező szerveren, illetve a pull requestek mentén.
3., Hasznos linkek/dokumentumok
>      A google docs: https://docs.google.com/document/d/1dEYXMGbmg9chdxa5HQaxr1VsbC7SsA5kw275p5kVN6U/edit?usp=sharing

>      DC szerver: https://discord.gg/peHM3ekc

>      A google java kódolási irányelvei hivatalosan: https://google.github.io/styleguide/javaguide.html

>      Egészítsd ki bármivel ami esetleg a projekt során felmerül, hivatkozásra kerül

4., SSH Authetntication elkészítése:
>      Nyisd meg a VSC-t
>
>      A terminálba írd be: ssh-keygen -t ed25519 -C "az_email_cimed@pelda.com" (Fontos hogy a githubos email címed add
>      meg amit fentebb írtam a pushról szóló irányelveknél)
>
>      A terminál ezután megkérdezi, hogy melyik fájlba mentse, ne írj be semmit csak nyomj entert.
>
>      A terminál ezután megkérdezi mi legyen a passphrese, itt is csak nyomj entert, de mivel még egyszer megkérdezi, hogy biztos-e,
>      ezért ott is nyomj entert
>
>      Ezután írd a terminálba: cat ~/.ssh/id_ed25519.pub
>
>      Az egész sor amit kiad azt másold ki, és a github settings fülén (fontos, hogy nem a projekt hanem a profil alapú settingsnél)
>      az SSH/GPG Keys fülén belül add key, adj neki vmi nevet, és másold be a kulcsot (ez nem signing key, hanem authentication)
>
>      Kész, yippee (Ha elakadtál volna/nem működik vmi nyugodtan kérdezz meg)
5., Aktuális fázis és a fázisban még el nem készült feladatok és a hozzá tartózó prioritások ("feladat kód - prio" páros)
>      Az_egész_projekt_lol_6769 - Super High
