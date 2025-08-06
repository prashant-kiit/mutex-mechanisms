import Redis from "ioredis";
import Redlock from "redlock";

const redisClients = [
  new Redis({ host: "127.0.0.1", port: 6379 }),
  new Redis({ host: "127.0.0.1", port: 6380 }),
  new Redis({ host: "127.0.0.1", port: 6381 }),
];

const redlock = new Redlock(redisClients, {
  retryCount: 5,
  retryDelay: 200, // count and wait-time for looping
});

async function main() {
  const resources = ["locks:user:123"]; // only resource is being tried to be accessed by multiple processes
  const ttl = 2000; // expiration time for locks

  try {
    const lock = await redlock.acquire(resources, ttl);
    console.log("Lock acquired on multiple resources");

    // Critical section

    await lock.release();
    console.log("Lock released on multiple resources");
  } catch (err) {
    console.error("Failed to acquire lock:", err);
  }
}

main();
