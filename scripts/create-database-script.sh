while IFS='=' read -r key value; do
  case $key in
  '#'*);;
  '');;
  *)
    value=$(echo "$value" | sed -e 's/^"//' -e 's/"$//')
    export "$key"=$value
    ;;
  esac
done < ../.env

postgresLeaderContainerName="postgres-leader"

echo "Creating database with name $DATABASE_NAME in PostgreSQL"

docker exec "$postgresLeaderContainerName" \
  psql -U "$POSTGRES_USERNAME" \
  -c "CREATE DATABASE $DATABASE_NAME;"