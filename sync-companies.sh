#! /bin/bash

echo "Syncing companies .."
http_response=$(curl -s -w "%{http_code}" -X POST http://localhost:8080/internal/sync-companies)
http_code=$(echo "$http_response" | tail -c 4)
if [ "$http_code" -eq 200 ]; then
    echo "$(date): Sync successful"
else
    echo "$(date): Sync failed with HTTP $http_code"
fi
