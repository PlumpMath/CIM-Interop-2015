using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OpenHRObfuscate
{
    class Program
    {
        static void Main(string[] args)
        {
            //args = new string[] { @"..\..\..\..\..\..\..\OpenHR-Original", @"..\..\..\..\..\..\..\OpenHR-Obfuscated" };

            try
            {
                int argsLength = args.WhenNotNull(t => t.Length);

                if ((argsLength != 1) && (argsLength != 2))
                {
                    WriteHelpMessage();
                    return;
                }

                OpenHRFile[] files = ReadInputFiles(args);

                Obfuscator obfuscator = new Obfuscator();
                obfuscator.Obfuscate(files);

                WriteOutputFiles(files);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                //Console.ReadLine();
            }
        }

        private static void WriteHelpMessage()
        {
            Console.WriteLine(string.Empty);
            Console.WriteLine("OpenHRObfuscate.exe");
            Console.WriteLine(string.Empty);
            Console.WriteLine("Usage");
            Console.WriteLine("-----");
            Console.WriteLine("OpenHRObfuscate input.xml                   (single file)");
            Console.WriteLine("OpenHRObfuscate input.xml output.xml        (single file)");
            Console.WriteLine("OpenHRObfuscate input_dir output_dir        (multiple files)");
        }

        private static OpenHRFile[] ReadInputFiles(string[] args)
        {
            bool useFileInput = true;
            string inputFile = null;
            string outputFile = null;
            string inputDir = null;
            string outputDir = null;

            if (args.Length == 1)
            {
                inputFile = args.First();
                outputFile = Path.Combine(Path.GetDirectoryName(inputFile), Path.GetFileNameWithoutExtension(inputFile) + "-output" + Path.GetExtension(inputFile));

                if (!File.Exists(inputFile))
                    throw new FileNotFoundException(string.Format("Could not find {0}", inputFile));
            }
            else
            {
                if (File.Exists(args.First()))
                {
                    inputFile = args.First();
                    outputFile = args.Skip(1).First();

                    if (Path.GetFullPath(inputFile) == Path.GetFullPath(outputFile))
                        throw new IOException("input.xml and output.xml are the same.");
                }
                else if (Directory.Exists(args.First()))
                {
                    useFileInput = false;
                    inputDir = args.First();
                    outputDir = args.Skip(1).First();

                    if (Directory.GetFiles(inputDir).Length == 0)
                        throw new IOException(string.Format("{0} is empty.", inputDir));

                    if (!Directory.Exists(outputDir))
                        Directory.CreateDirectory(outputDir);

                    if (Path.GetFullPath(inputDir) == Path.GetFullPath(outputDir))
                        throw new IOException("input_dir and output_dir are the same.");
                }
                else
                {
                    throw new IOException(string.Format("{0} does not exist as a file or directory.", args.First()));
                }
            }

            List<OpenHRFile> files = new List<OpenHRFile>();

            if (useFileInput)
            {
                files.Add(new OpenHRFile(inputFile, File.ReadAllText(inputFile), outputFile));
            }
            else
            {
                files.AddRange(Directory
                    .GetFiles(inputDir)
                    .Select(t =>
                        new OpenHRFile
                        (
                            inputFilename: t,
                            inputText: File.ReadAllText(t),
                            outputFilename: Path.Combine(outputDir, Path.GetFileName(t))
                        )));
            }

            return files.ToArray();
        }

        private static void WriteOutputFiles(OpenHRFile[] files)
        {
            foreach (OpenHRFile file in files)
                File.WriteAllText(file.OutputFilename, file.OutputText);
        }
    }
}
