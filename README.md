<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->
<a name="readme-top"></a>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/jeffersonallves/api-email">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">api-email</h3>

  <p align="center">
    An awesome README template to jumpstart your projects!
    <br />
    <a href="https://github.com/jeffersonallves/Bapi-email"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/jeffersonallves/api-email">View Demo</a>
    ·
    <a href="https://github.com/jeffersonallves/api-email/issues">Report Bug</a>
    ·
    <a href="https://github.com/jeffersonallves/api-email/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot]](https://example.com)

There are many great README templates available on GitHub; however, I didn't find one that really suited my needs so I created this enhanced one. I want to create a README template so amazing that it'll be the last one you ever need -- I think this is it.

Here's why:
* Your time should be focused on creating something amazing. A project that solves a problem and helps others
* You shouldn't be doing the same tasks over and over like creating a README from scratch
* You should implement DRY principles to the rest of your life :smile:

Of course, no one template will serve all projects since your needs may be different. So I'll be adding more in the near future. You may also suggest changes by forking this repo and creating a pull request or opening an issue. Thanks to all the people have contributed to expanding this template!

Use the `BLANK_README.md` to get started.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.

* [![Spring-Framework][Spring-Framework]][Spring-Framework-url]
* [![Spring-Data][Spring-Data]][Spring-Data-url]
* [![Spring Security][Spring-Security]][Spring-Security-url]
* [![RabbitMQ][RabbitMQ]][RabbitMQ-url]
* [![Swagger][Swagger]][Swagger-url]
* [![Docker][Docker]][Docker-url]
* [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
* [![Maven][Maven]][Maven-url]
* [![JUnit][JUnit]][JUnit-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

_Below is an example of how you can instruct your audience on installing and setting up your app. This template doesn't rely on any external dependencies or services._

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/your_username_/Project-Name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

#### Controller E-mail - "emails"

#### POST

- [x] Envio de e-mail simples sem anexo
- [x] Envio de e-mail com anexo
- [x] Envio de e-mail com template
- [x] Envio de E-mail Geral - Descrição: Envio de e-mail simples, com anexo(s) ou com template

#### GET

- [x] Busca de e-mail por id
- [x] Busca de todos e-mails enviados
- [x] Busca e-mails enviados por status ou data de envio
- [x] Busca e-mails enviados por status
- [x] Busca e-mails enviados por data de envio
- [x] Busca e-mails enviados entre datas
- [x] Busca de logs de e-mails enviados com falha por data ou por id

----------------------------------------------------------------------------------
#### Controller Autorização - "auth"

#### POST

- [x] Login
- [x] Registro de usuários
Unidade 03 - Gestão Ágil de Requisitos

#### GET

- [x] Buscar todos usuários cadastrados

#### DELETE

- [x] Deletar usuários

-----------------------------------------
#### Caracteristicas

- [x] API Dockerizada
- [x] API Documentada com swagger
- [x] Tratamento de exceções
- [x] Autorização com Spring Security
- [x] Owner red será o usuário logado
- [x] Customizar o banner no console da API
- [x] Dividir perfils desenvolvimento e produção
- [x] Parametrizar com variáveis de ambiente as propriedades da aplicação
- [x] Parametrizado o Schedule se irá permitir envio ou não de e-mail
- [x] Gravar logs de erros quando acontece um problema ao enviar e-mails
- [x] Parametrizado o e-mail responsável pelo envio
- [x] Parametrizado o tempo do cron
- [x] Reenvio de e-mails com falha (RabbitMQ)
- [x] Gravar log de erros a partir do ControllerAdvice
- [x] Testes Unitários automatizados
- [x] Permitir formatar o e-mail a ser enviado com thymeleaf
- [x] Publicar no repositório GIT
- [x] Cadastrar um registro por destinatário dentro do Redis (Fila)
- [x] Enviar para uma lista de e-mails de forma agendada através do REDIS
- [x] README
- [x] Migration para produção
- [x] Aspectos
- [x] Template com thymeleaf utilizando variáveis
- [x] Status do e-mail: SEND, ERROR, PROCESSING
- [x] Tipos de usuários: ADMIN, USER
- [x] Banco de dados com postgres
- [x] Utilizar Maven

See the [open issues](https://github.com/jeffersonallves/api-email/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.md` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Your Name - [@your_twitter](https://twitter.com/your_username) - email@example.com

Project Link: [https://github.com/your_username/repo_name](https://github.com/your_username/repo_name)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites to kick things off!

* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/jeffersonallves/api-email.svg?style=for-the-badge
[contributors-url]: https://github.com/jeffersonallves/api-email/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/jeffersonallves/api-email.svg?style=for-the-badge
[forks-url]: https://github.com/jeffersonallves/api-email/network/members
[stars-shield]: https://img.shields.io/github/stars/jeffersonallves/api-email.svg?style=for-the-badge
[stars-url]: https://github.com/jeffersonallves/api-email/stargazers
[issues-shield]: https://img.shields.io/github/issues/jeffersonallves/api-email.svg?style=for-the-badge
[issues-url]: https://github.com/jeffersonallves/api-email/issues
[license-shield]: https://img.shields.io/github/license/jeffersonallves/api-email.svg?style=for-the-badge
[license-url]: https://github.com/jeffersonallves/api-email/blob/master/LICENSE.md
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/jefferson-alves-388ab791
[product-screenshot]: images/screenshot.png
[Spring-Framework]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-Framework-url]: https://spring.io/
[Spring-Data]: https://img.shields.io/badge/Spring_Data-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-Data-url]: https://spring.io/projects/spring-data
[Spring-Security]: https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white
[Spring-Security-url]: https://spring.io/projects/spring-security
[RabbitMQ]: https://img.shields.io/badge/rabbitmq-%23FF6600.svg?&style=for-the-badge&logo=rabbitmq&logoColor=white
[RabbitMQ-url]: https://www.rabbitmq.com/
[Swagger]: https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white
[Swagger-url]: https://swagger.io/
[Docker]: https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Maven]: https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org/
[JUnit]: https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white
[JUnit-url]: https://junit.org/junit5/
