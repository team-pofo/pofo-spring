from selenium import webdriver
from selenium.webdriver.common.by import By
import time
import pandas as pd
import random

driver = webdriver.Chrome()
driver.get("https://stackshare.io/tools/top")

click_count = 0
total_count = 0
max_clicks = 5

while click_count < max_clicks and total_count < 80:
    try:
        more_button = driver.find_element(By.CLASS_NAME, 'trending-tab-load-more-services')
        more_button.click()

        click_count = 0
        total_count += 1
        time.sleep(random.uniform(2, 4))
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(random.uniform(2, 4))

    except Exception as e:
        print("More 버튼을 더 이상 찾을 수 없습니다.")
        click_count += 1
        if click_count >= max_clicks:
            break

data = []
items = driver.find_elements(By.CSS_SELECTOR, '.trending-item-container')

for item in items:
    try:
        stack_name = item.find_element(By.CSS_SELECTOR, '#service-name-trending').text
    except:
        stack_name = ''

    try:
        stack_type = item.find_element(By.CSS_SELECTOR, '[itemprop="applicationSubCategory"]').text
    except:
        stack_type = ''

    try:
        image_url = item.find_element(By.CSS_SELECTOR, '.tool-logo img').get_attribute('src')
    except:
        image_url = ''

    try:
        homepage_url = item.find_element(By.CSS_SELECTOR, '.web-link').get_attribute('href')
    except:
        homepage_url = ''

    data.append([stack_name, stack_type, image_url, homepage_url])

df = pd.DataFrame(data, columns=['stack_name', 'stack_type', 'image_url', 'homepage_url'])
df.to_csv('stack_dataset.csv', index=False, encoding='utf-8-sig')

driver.quit()
