

# De Perlas Portfolio Viewer

## Overview

This Spring Boot application helps you manage your investment portfolio effectively. This is the result of my incursion to Spring Boot framework.

**Key Features:**

- Track various investments (stocks, cryptocurrencies, traditional assets)
- Get real-time price updates for cryptocorrencies, ARS to blue exchange rate and other currencies to USD thanks to api fetchers
- Total portfolio value in USD


## Technologies Used

- Spring Boot
- Hibernate
- Thymeleaf
- MariaDB


## Getting Started

1. **Clone the repository:**

   ```bash
   git clone [https://github.com/tompy017/portfolio-viewer.git](https://github.com/tompy017/portfolio-viewer.git)
   ```

2. **Build the application:**
    ```bash
    mvn clean install
    ```


3. **Create a config.properties file:**

    Create a file named config.properties in the root directory.
    Add the following lines with your credentials:

        db.username="database_username"
        db.password="database_password"
        api.freecurrencyapi.key="your_api_key"


4. **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

5. **Access the application:**

    Open http://localhost:8080/investments/ in your web browser.



## API Credits
This application utilizes the following APIs:

- **ARS Blue Rate Fetcher:**
    - Provider: Bluelytics
    - Website: http://www.bluelytics.com.ar
    - Contact: pabloseibelt@sicarul.com

- **Cryptocurrency Fetcher:**
    - Provider: CoinGecko
    - Website: https://www.coingecko.com

- **Forex Rate Fetcher:**

    - Provider: Free Currency API
    - Website: https://freecurrencyapi.com


## License


This project is licensed under the GNU General Public License v3.0.
See the [LICENSE](LICENSE) file for details.