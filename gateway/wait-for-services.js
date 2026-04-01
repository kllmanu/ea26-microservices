const http = require('http');
const https = require('https');

const urls = process.argv.slice(2);

if (urls.length === 0) {
    console.error('No URLs provided. Usage: node wait-for-services.js <url1> <url2> ...');
    process.exit(1);
}

const checkUrl = (url) => {
    const protocol = url.startsWith('https') ? https : http;
    return new Promise((resolve) => {
        const req = protocol.get(url, (res) => {
            if (res.statusCode === 200) {
                resolve(true);
            } else {
                resolve(false);
            }
        });
        req.on('error', () => resolve(false));
        req.setTimeout(5000, () => {
            req.destroy();
            resolve(false);
        });
        req.end();
    });
};

const waitForUrls = async () => {
    for (const url of urls) {
        let ready = false;
        while (!ready) {
            console.log(`Waiting for ${url}...`);
            ready = await checkUrl(url);
            if (!ready) {
                await new Promise((resolve) => setTimeout(resolve, 5000));
            }
        }
        console.log(`${url} is ready!`);
    }
};

waitForUrls().catch((err) => {
    console.error('An error occurred while waiting for services:', err);
    process.exit(1);
});
