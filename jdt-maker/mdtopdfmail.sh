$F_NAME

for i in "$@"; do
  case $i in
    -f=*|--file=*)
      F_NAME="${i#*=}"
      shift # past argument=value
    ;;
  esac
done

echo "$F_NAME"

pandoc $F_NAME.md -o $F_NAME.pdf
# echo "Test2" | mail -s "Test1" alexandre.javet@hotmail.com -A ./JDT2020-07-20.pdf
