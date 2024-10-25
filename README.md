# grw  (java)
_dev.habibi.grw_  

&nbsp;  
&nbsp;  

## TABLE OF CONTENTS
* [Description](#description)
* [Attention](#attention)
* [Rights, License and Disclaimer](#rights)
* [Versioning Guideline](#versioning)
* [Features](#features)
* [Author](#author)  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="description"></a>
## DESCRIPTION
* SUBJECT: Java application to change wallpapers Gnome-Desktop;
* PROJECT NAME: *grw* - Gnome Randomic Wallpaper;
* LIBRARY NAME: ;
* WHAT IS: *grw* is a small software aims to change the wallpaper image of Gnome Desktop randomly. Developed for Linux with Gnome Desktop Environment. More information about versions and compatibility can be found below;
* To wit: The *grw* and its resources are a project sample, which I have developing to demonstrate some of my abilities. The _*grw*_ project belongs to my personal portfolio. Its detailed project can be followed just here on GitHub: [github.com/users/ROPIMASI/projects/](https://github.com/users/ROPIMASI/projects/). It is also found on my web-portfolio: [habibi.dev/portfolio](https://habibi.dev/portfolio).  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="attention"></a>
## ATTENTION
>**IMPORTANT NOTE / DISCLAIMER:** (en-US)
>This is a personal, private project, solely for the purpose of my studies
in software development, and for the sole and exclusive use of mine.
It is a project under development and experimentation, therefore I have no
responsibility for the information contained therein, nor about its results
and effects when used.
  
>**NOTA IMPORTANTE / ISENÇÃO DE RESPONSABILIDADE:** (pt-BR)
>Este é um projeto pessoal, particular, com finalidade exclusiva de meus estudos
em desenvolvimento de software, e de uso e fruto único e exclusivamente meus.
Trata-se de um projeto em fase de desenvovimento e experimentações, portanto não
tenho nenhuma responsabilidade pelas informações nele contidas, tão pouco sobre
seus resultados e efeitos ao ser utilizado.  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="rights"></a>
## RIGHTS, LICENSE AND DISCLAIMER: (en-US)
This file is an integral, indivisible, inseparable part of a particular project, which has its use expressly exclusive to its author, Ronaldo Marques (ronaldomarques@email.com / http://ronaldomarques.dev);  
Any use, sale, rental, distribution, in parts or integral of this project is prohibited;  
This project has the sole purpose of demonstrating knowledge and skills in software development;  
Therefore, the author of this project does not recognize or assume any responsibility for the use of it, neither for any possible reflexes or consequence of such use.  

## DIREITOS, LICENSA E ISENÇÃO DE RESPONSABILIDADE: (pt-BR)
Este arquivo é parte integrante, indivisível, inseparável de um projeto particular, o qual tem seu uso e fruto expressamente exclusivo à seu autor, Ronaldo Marques (ronaldomarques@email.com / http://ronaldomarques.dev);  
É vetado qualquer utilização, venda, aluguél, distribuição, em partes ou integral deste projeto;  
Este projeto tem finalidade exclusiva de demonstração de conhecimento e habilidades no desenvolvimento de software;  
Sendo assim, o autor deste projeto não reconhece nem assume qualquer responsabilidade pela utilização do mesmo, tão pouco por qualquer possível reflexos ou consequência de tal utilização.  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="versioning"></a>
## VERSIONING GUIDELINE
In a team project, it is very important to know and follow the specifications of the project version. Although at the moment this roject's status is under early development (as it has not its first release version yet 1.0.0-release) however its project already is designed under [**_SemVer_** (Semantic Versioning Specification)](http://semver.org/).  
Thus, this project uses **_SemVer_** for its versioning. **_SemVer_** is a specification (set of rules) that tells us (or dictates) how to use the numbers (and some letters) on the _versioning-expression_ (_VerExpr_). More specifically, this project uses the following standardization: **_Major.Minor.Patch-ReleaseStatus+Build_**, where:
* The standard values of **_Major_**, **_Minor_**, and **_Patch_** for the _VerExpr_ are as follows:
  + Positive integer decimal numbers, without zero remaining on the left;
  + **_Major_** version represents wider changes in the project, which affects the main structure of the project, or its main objectives, or the last user API released;
  + **_Minor_** version represents smaller changes in the project, which don't affect above itens, but affect the amount of the application fuatrures with a new one or more, or remove an existing feature previouslly released;
  + **_Patch_** version represents specific changes which goals to fix or improve some feature, or undesired behavior in the application.  
* The standard flags of **_ReleaseStatus_** for the _VerExpr_ are as follows:
  + **_dev_**: in early development, usage not encouraged;
  + **_alpha_**: in development, first test phase, it's encouraged usage for **test only** by people involved with software development, at self-own risk;
  + **_beta_**: in pre-release version, general public usage is acceptable, however, **only for test**, usage is a choice at self-own risk;
  + **_release_**: release version; relatively stable in proportion to the effectiveness of the tests; bugs are possible to appear, so it would come back to a _hotfix-branch_ if needed.
* The standard values of **_Build_** for the _VerExpr_ are as follows:
  + A 12-digit numeric sequence, positive integer decimal digits, formatted somewhat to ISO 8601 DateTime YYYYMMDDhhmm;
  + The initial 4 digits (YYYY) represent the year;
  + The next 2 digits (MM) represent the month;
  + The next 2 digits (DD) represent the day;
  + The next 2 digits (hh) represent the hour;
  + The following 2 digits (mm) represent the minutes;
  + All of aboce referecing to the moment when the developer builds/exports the .JAR file (* 1).  
  
> (* 1) The numerical sequence _Build_ necessarily/obligatorily refers to Greenwich Mean Time (GMT), also known as  Universal Time Coordinate (UTC), or "Z time" or "Zulu time".  
  
> Example of **_Build_**: "202312311859".
  
> Full example of **_Versioning-Expression_**: `1.2.3-release+202312311859`, meaning `1`._ ._ version fully implemented according to the project and its backlog; added by _ .`2`._ additionals features to the main version, according to the project backlog and its issues priorities in the **_SCRUM life cycle_**; added by _ ._ .`3` patches fixed in this mentioned lastest version following the **_GITFLOW life cycle_**, that means, it is a released version after passed by the tests in **_alpha_** and **_beta_** pre-releases; and finally, it was specifically build at the year 2023 month 12 (December) day 31 at 18:59h at UTC/GMT/Z-time/Zulu-time (18hours and 59minutes equal 6p.m and 59minutes in some idioms).  

&nbsp;  
&nbsp;  
&nbsp;  

<a name="features"></a>
## FEATURES
### Features in current version (0.7.1)
  + Prints syntax and brief option help to the screen;
  + Prints the current version of *grw* to the screen;
  + Prints the current status of the configuration set to the screen;
  + Sets the timeout for automatically changing the wallpaper;
  + Sets a directory as the image directory;
  + Starts the infinite loop for image changing;
  + Stops the infinite loop for image changing;  
  + Sets the default value for all configurations;
  + Prints the current directory-path witch is working on, and its list of images will be placed as wallpaper;

&nbsp;  

### Features in target release version (1.0.0)
  + All features above, more...
  + Handle 'n' different image directories along their aliases;

&nbsp;  
&nbsp;  
&nbsp;  

<a name="author"></a>
## Att. Ronaldo Pimentel Marques da Silva.
##### | [https://ropimasi.dev](https://ropimasi.dev) | [linkedin @ropimasi](https://linkedin.com/in/ropimasi/) | [twitter @ropimasi](https://twitter.com/ropimasi/) | [insta @ropimasi](https://instagram.com/ropimasi/) | [ropimasi@email.com](mailto://ropimasi@email.com) |  

&nbsp;  
&nbsp;  

